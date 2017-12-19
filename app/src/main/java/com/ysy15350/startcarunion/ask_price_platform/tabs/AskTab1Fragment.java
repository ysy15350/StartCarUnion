package com.ysy15350.startcarunion.ask_price_platform.tabs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_AskPricieInfo;
import com.ysy15350.startcarunion.ask_price_platform.AskPriceDetailActivity;

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
public class AskTab1Fragment extends MVPBaseListViewFragment<AskTab1ViewInterface, AskTab1Presenter>
        implements AskTab1ViewInterface {


    @Override
    public AskTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new AskTab1Presenter(getActivity());
    }

    @Override
    public void initView() {
        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        setFormHead("询价平台");
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        // TODO Auto-generated method stub
        mPresenter.inquiry_list(page, pageSize);
    }

    @Override
    public void inquiry_listCallback(boolean isCache, Response response) {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Object item = parent.getItemAtPosition(position);
        if (item != null && item instanceof AskPricieInfo) {
            AskPricieInfo askPricieInfo = (AskPricieInfo) item;
            int ask_id = askPricieInfo.getId();
            if (ask_id > 0) {
                Intent intent = new Intent(mContext, AskPriceDetailActivity.class);
                intent.putExtra("ask_id", ask_id);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void back() {
        super.back();
        getActivity().finish();
    }
}
