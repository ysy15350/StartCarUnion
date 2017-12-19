package com.ysy15350.startcarunion.mine.my_call;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyCall;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyCall;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/6.
 */

@ContentView(R.layout.activity_list)
public class MyCallActivity extends MVPBaseListViewActivity<MyCallViewInterface, MyCallPresenter>
        implements MyCallViewInterface {

    @Override
    protected MyCallPresenter createPresenter() {

        return new MyCallPresenter(MyCallActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        setFormHead("已拨电话");

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
        mPresenter.dial_record(page, pageSize);
    }

    @Override
    public void dial_recordCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<MyCall> list = response.getData(new TypeToken<List<MyCall>>() {
                    }.getType());
                    bindData(list);
                }

                xListView.stopRefresh();
                xListView.stopLoadMore();

                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ListViewAdpater_MyCall mAdapter;
    List<MyCall> mList = new ArrayList<MyCall>();


    public void bindData(List<MyCall> list) {


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
        mAdapter = new ListViewAdpater_MyCall(this, mList);

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