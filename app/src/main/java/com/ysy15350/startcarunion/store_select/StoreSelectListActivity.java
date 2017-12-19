package com.ysy15350.startcarunion.store_select;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_CarType_Select;
import com.ysy15350.startcarunion.store_select.utils.PinyinComparator;
import com.ysy15350.startcarunion.store_select.utils.SortAdapter;
import com.ysy15350.startcarunion.store_select.utils.SortModel;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.CarType;
import base.mvp.MVPBaseListViewActivity;
import common.CharacterParser;
import common.CommFunAndroid;
import custom_view.SideBar;

@ContentView(R.layout.activity_store_list_select)
public class StoreSelectListActivity extends MVPBaseListViewActivity<StoreSelectListViewInterface, StoreSelectListPresenter>
        implements StoreSelectListViewInterface {

    @Override
    protected StoreSelectListPresenter createPresenter() {

        return new StoreSelectListPresenter(StoreSelectListActivity.this);
    }

    @ViewInject(R.id.tv_Character)
    private TextView tv_Character;

    /**
     * 右侧字母列表
     */
    @ViewInject(R.id.sidrbar)
    private SideBar sideBar;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        //xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);

        setFormHead("公司经营品牌");

        setMenuText("确定");

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(tv_Character);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    xListView.setSelection(position);
                }

            }
        });

    }

    @Override
    public void initData() {

        super.initData();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        page = 1;
        initData(page, pageSize);
    }


    @Override
    public void initData(int page, int pageSize) {

        mPresenter.type_list(page, pageSize);
    }

    ListViewAdpater_CarType_Select mAdapter;
    List<CarType> mList = new ArrayList<CarType>();

    private SortAdapter sortAdapter;
    private List<SortModel> mSourceDateList;


    @Override
    public void bindCarType(Response response) {
        // TODO Auto-generated method stub
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                List<CarType> list = response.getData(new TypeToken<List<CarType>>() {
                }.getType());

                List<SortModel> sourceDateList = filledData(list);

                if (page == 1) {
                    mList = new ArrayList<CarType>();
                    mSourceDateList = new ArrayList<>();
                } else {
                    if (list == null || list.isEmpty())
                        showMsg("没有更多了");
                }

                if (list != null)
                    mList.addAll(list);
                mSourceDateList.addAll(sourceDateList);

                mAdapter = new ListViewAdpater_CarType_Select(this, mList);
                sortAdapter = new SortAdapter(this, mSourceDateList);

                bindListView(sortAdapter);// 调用父类绑定数据方法

                // xListView.notify();

                if (list != null || !list.isEmpty()) {

                    page++;
                }
            }
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param list
     * @return
     */
    private List<SortModel> filledData(List<CarType> list) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < list.size(); i++) {
            CarType carType = list.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setId(carType.getId());
            sortModel.setName(carType.getTitle());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(carType.getTitle());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    List<SortModel> mSeletedCarType = new ArrayList<SortModel>();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        View img_seleted = view.findViewById(R.id.img_seleted);
        SortModel sortModel = (SortModel) parent.getItemAtPosition(position);
        if (sortModel.isSeleted()) {
            if (mSeletedCarType.contains(sortModel))
                mSeletedCarType.remove(sortModel);
            img_seleted.setVisibility(View.GONE);
            sortModel.setSeleted(false);
        } else {
            mSeletedCarType.add(sortModel);
            img_seleted.setVisibility(View.VISIBLE);
            sortModel.setSeleted(true);
        }
    }

    String brand = "";
    String names = "";

    @Event(value = R.id.tv_menu)
    private void tv_menuClick(View view) {
        brand = "";
        try {
            if (mSeletedCarType != null && !mSeletedCarType.isEmpty()) {

                for (SortModel sortModel :
                        mSeletedCarType) {
                    if (sortModel != null) {
                        brand = brand + sortModel.getId() + "@";
                        names = names + sortModel.getName() + ",";
                    }
                }
            }

            String str = brand.substring(0, brand.length() - 1);

            Intent intent = getIntent();
            intent.putExtra("brand", str);
            intent.putExtra("brand_names", names.substring(0, names.length() - 1));

            setResult(RESULT_OK, intent);

            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mAdapter != null)
                mAdapter.destoryImageView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
