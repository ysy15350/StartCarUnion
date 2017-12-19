package com.ysy15350.startcarunion.mine.my_comment;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyComment;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyComment;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

@ContentView(R.layout.activity_list)
public class MyCommentActivity extends MVPBaseListViewActivity<MyCommentViewInterface, MyCommentPresenter>
        implements MyCommentViewInterface {

    @Override
    protected MyCommentPresenter createPresenter() {

        return new MyCommentPresenter(MyCommentActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        setFormHead("我的评论");

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
        mPresenter.comment_list(page, pageSize);
    }

    @Override
    public void comment_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<MyComment> list = response.getData(new TypeToken<List<MyComment>>() {
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

    ListViewAdpater_MyComment mAdapter;
    List<MyComment> mList = new ArrayList<MyComment>();


    public void bindData(List<MyComment> list) {


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
        mAdapter = new ListViewAdpater_MyComment(this, mList);

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
