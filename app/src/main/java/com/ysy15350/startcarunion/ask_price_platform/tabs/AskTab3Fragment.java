package com.ysy15350.startcarunion.ask_price_platform.tabs;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_AskPricieInfo;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.AskPricieInfo;
import base.mvp.MVPBaseListViewFragment;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_list)
public class AskTab3Fragment extends MVPBaseListViewFragment<AskTab3ViewInterface, AskTab3Presenter>
        implements AskTab3ViewInterface {


    @Override
    public AskTab3Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new AskTab3Presenter(getActivity());
    }

    @Override
    public void initView() {
        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        setFormHead("我的发布");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        // TODO Auto-generated method stub
        mPresenter.use_inquiry_info(page, pageSize);
    }

    @Override
    public void inquiry_infoCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<AskPricieInfo> list = response.getData(new TypeToken<List<AskPricieInfo>>() {
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

    ListViewAdpater_AskPricieInfo mAdapter;
    List<AskPricieInfo> mList = new ArrayList<AskPricieInfo>();

    public void bindData(List<AskPricieInfo> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty())
                showMsg("没有更多了");
        }

        mList.addAll(list);
        mAdapter = new ListViewAdpater_AskPricieInfo(mContext, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        xListView.notify();

        if (list != null || !list.isEmpty()) {
            page++;
        }

    }

    @Override
    protected void back() {
        super.back();
        getActivity().finish();
    }


}
