package com.ysy15350.startcarunion.mine.my_visitor;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyVisitor;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyFootprint;
import api.model.StoreInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

@ContentView(R.layout.activity_list)
public class MyVisitorActivity extends MVPBaseListViewActivity<MyVisitorViewInterface, MyVisitorPresenter>
        implements MyVisitorViewInterface {

    @Override
    protected MyVisitorPresenter createPresenter() {

        return new MyVisitorPresenter(MyVisitorActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        setFormHead("我的访客");

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

    ListViewAdpater_MyVisitor mAdapter;
    List<StoreInfo> mList = new ArrayList<StoreInfo>();

    @Override
    public void visitors_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<MyFootprint> list = response.getData(new TypeToken<List<MyFootprint>>() {
                    }.getType());

                    bindData(getStoreInfoList(list));
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
                            storeInfo_list.get(0).setCount(myFootprint.getCount());
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
        mAdapter = new ListViewAdpater_MyVisitor(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(mAdapter!=null)
                mAdapter.destoryImageView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
