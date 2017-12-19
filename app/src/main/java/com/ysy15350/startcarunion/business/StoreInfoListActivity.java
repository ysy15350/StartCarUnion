package com.ysy15350.startcarunion.business;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_StoreInfo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.StoreInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.model.ScreenInfo;
import custom_view.popupwindow.CallPopupWindow;

@ContentView(R.layout.activity_store_list)
public class StoreInfoListActivity extends MVPBaseListViewActivity<StoreInfoListViewInterface, StoreInfoListPresenter>
        implements StoreInfoListViewInterface {

    @Override
    protected StoreInfoListPresenter createPresenter() {

        return new StoreInfoListPresenter(StoreInfoListActivity.this);
    }

    @ViewInject(R.id.et_keywords)
    private EditText et_keywords;

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        et_keywords.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    search();
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        changeHeight();

        page = 1;
        initData(page, pageSize);
    }

    int listview_item_height = 300;


    private void changeHeight() {
        ScreenInfo screenInfo = CommFunAndroid.getScreenInfo(this);
        if (screenInfo != null) {
            int height = screenInfo.getHeight();//屏幕高度
            RelativeLayout rl_head = mHolder.getView(R.id.rl_head);//标题栏
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rl_head.getLayoutParams();
            if (params != null) {
                int rl_head_height = params.height;

                int listviewHeight = height - rl_head_height;
                listview_item_height = listviewHeight / 5;

            }
        }
    }

    private int flag, pid, city_id;

    private String keywords;

    @Override
    public void initData(int page, int pageSize) {
        pageSize = 10;
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra("flag", -1);
            pid = intent.getIntExtra("cartype_id", -1);
            city_id = intent.getIntExtra("city_id", -1);
            //showMsg("city_id:" + city_id);
        }
        CommFunMessage.showWaitDialog(this, "数据加载中...");
        mPresenter.store_list(flag, pid, city_id, keywords, page, pageSize);
    }

    ListViewAdpater_StoreInfo mAdapter;
    List<StoreInfo> mList = new ArrayList<StoreInfo>();

    private void bindBusinessList(List<StoreInfo> list) {


        try {
            if (list != null && !list.isEmpty()) {
                if (page == 1) {
                    mList = new ArrayList<StoreInfo>();
                } else {
                    if (list == null || list.isEmpty()) {
                        showMsg("没有更多了");
                        xListView.stopLoadMore();
                    }
                }

                mList.addAll(list);
                mAdapter = new ListViewAdpater_StoreInfo(this, mList, listview_item_height);

                mAdapter.setAdapterListener(new ListViewAdpater_StoreInfo.AdapterListener() {
                    @Override
                    public void onCall(StoreInfo storeInfo) {
                        final int uid = storeInfo.getId();

                        List<String> phones = new ArrayList<>();

                        String mobile = storeInfo.getMobile();
                        if (!CommFunAndroid.isNullOrEmpty(mobile))
                            phones.add(mobile);

                        String tell = storeInfo.getTell();


                        if (!CommFunAndroid.isNullOrEmpty(tell)) {

                            String[] phonesArr = tell.split("/");

                            if (phonesArr != null && phonesArr.length > 0) {
                                for (String phone :
                                        phonesArr) {
                                    phones.add(phone);
                                }
                            }
                        }

                        if (phones == null || phones.size() == 0) {
                            return;
                        }


                        CallPopupWindow popupWindow = new CallPopupWindow(StoreInfoListActivity.this, phones);

                        popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                            @Override
                            public void onCallMobile(String mobile) {
                                mPresenter.make_tell(uid, mobile);
                                //showMsg(mobile);
                                callPhoneCheckPermission(StoreInfoListActivity.this, mobile);
                            }


                            @Override
                            public void onCancelClick() {

                            }
                        });
                        popupWindow.showPopupWindow(mContentView);
                    }
                });

                bindListView(mAdapter);// 调用父类绑定数据方法

                //xListView.notify();

                if (list != null || !list.isEmpty()) {
                    page++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void make_tellCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                }

                //showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store_listCallback(boolean isCache, Response response) {
        CommFunMessage.hideWaitDialog();
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<StoreInfo> list = response.getData(new TypeToken<List<StoreInfo>>() {
                    }.getType());
                    bindBusinessList(list);
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转到商家列表
     *
     * @param view
     */
    @Event(value = R.id.img_search)
    private void img_searchClick(View view) {
        search();
    }

    private void search() {
        page = 1;
        keywords = mHolder.getViewText(R.id.et_keywords);
        initData(page, pageSize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        try {
            StoreInfo storeInfo = (StoreInfo) parent.getItemAtPosition(position);
            if (storeInfo != null) {
                Intent intent = new Intent(this, StoreInfoDetailActivity.class);
                intent.putExtra("id", storeInfo.getId());
                intent.putExtra("fullname", storeInfo.getFullname());
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
