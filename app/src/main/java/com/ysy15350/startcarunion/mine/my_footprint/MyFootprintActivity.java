package com.ysy15350.startcarunion.mine.my_footprint;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyFootprint;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyFootprint;
import api.model.StoreInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import custom_view.popupwindow.CallPopupWindow;

@ContentView(R.layout.activity_list)
public class MyFootprintActivity extends MVPBaseListViewActivity<MyFootprintViewInterface, MyFootprintPresenter>
        implements MyFootprintViewInterface {

    @Override
    protected MyFootprintPresenter createPresenter() {

        return new MyFootprintPresenter(MyFootprintActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        setFormHead("我的足迹");

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
        showWaitDialog("数据加载中...");
        mPresenter.visitors_list(page, pageSize);
    }

    @Override
    public void visitors_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<MyFootprint> list = response.getData(new TypeToken<List<MyFootprint>>() {
                    }.getType());

                    List<StoreInfo> storeList = getStoreInfoList(list);

                    bindData(storeList);
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();

                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<StoreInfo> getStoreInfoList(List<MyFootprint> list) {
        try {
            if (list != null && !list.isEmpty()) {
                List<StoreInfo> storeList = new ArrayList<>();
                for (MyFootprint myFootprint :
                        list) {
                    if (myFootprint != null) {
                        List<StoreInfo> storeInfo_list = myFootprint.getList();
                        if (storeInfo_list != null && !storeInfo_list.isEmpty()) {
                            storeInfo_list.get(0).setDate(myFootprint.getDate());
                            storeList.addAll(storeInfo_list);
                        }
                    }
                }

                return storeList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ListViewAdpater_MyFootprint mAdapter;
    List<StoreInfo> mList = new ArrayList<StoreInfo>();

    public void bindData(List<StoreInfo> list) {

        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_MyFootprint(this, mList);

        mAdapter.setAdapterListener(new ListViewAdpater_MyFootprint.AdapterListener() {
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


                CallPopupWindow popupWindow = new CallPopupWindow(MyFootprintActivity.this, phones);

                popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                    @Override
                    public void onCallMobile(String mobile) {
                        mPresenter.make_tell(uid, mobile);
                        //showMsg(mobile);
                        callPhoneCheckPermission(MyFootprintActivity.this, mobile);
                    }


                    @Override
                    public void onCancelClick() {

                    }
                });
                popupWindow.showPopupWindow(mContentView);
            }
        });

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
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
                showMsg(msg);
            }
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
