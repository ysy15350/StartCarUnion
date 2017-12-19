package com.ysy15350.startcarunion.mine.my_score;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyScore;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyScore;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

@ContentView(R.layout.activity_my_score)
public class MyScoreActivity extends MVPBaseListViewActivity<MyScoreViewInterface, MyScorePresenter>
        implements MyScoreViewInterface {

    @Override
    protected MyScorePresenter createPresenter() {

        return new MyScorePresenter(MyScoreActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        xListView.setPullLoadEnable(false);

        setFormHead("我的积分");

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
        mPresenter.sign_list(page, pageSize);
    }

    @Override
    public void sign_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mHolder.setText(R.id.tv_score, response.getPoint() + "");
                    List<MyScore> list = response.getData(new TypeToken<List<MyScore>>() {
                    }.getType());
                    bindData(list);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ListViewAdpater_MyScore mAdapter;
    List<MyScore> mList = new ArrayList<MyScore>();

    public void bindData(List<MyScore> list) {


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
        mAdapter = new ListViewAdpater_MyScore(this, mList);

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
