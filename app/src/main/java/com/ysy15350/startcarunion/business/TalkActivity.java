package com.ysy15350.startcarunion.business;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_Talk;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.MyComment;
import base.mvp.MVPBaseListViewActivity;
import common.AndroidBug5497Workaround;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/6.
 */

@ContentView(R.layout.activity_talk_list)
public class TalkActivity extends MVPBaseListViewActivity<TalkViewInterface, TalkPresenter>
        implements TalkViewInterface {

    @Override
    protected TalkPresenter createPresenter() {

        return new TalkPresenter(TalkActivity.this);
    }

    private int mUid;

    @Override
    public void initView() {

        super.initView();

        AndroidBug5497Workaround.assistActivity(this);//解决底部输入框键盘遮挡问题

        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        xListView.setPullLoadEnable(false);
        setFormHead("和商家交谈中");

    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        mUid = intent.getIntExtra("id", -1);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initData(page, pageSize);
    }

    @Override
    public void onRefresh() {
        page++;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        mPresenter.comment_view(mUid);//设置留言状态为已浏览状态
        mPresenter.comment_list(mUid, page, pageSize);
    }

    ListViewAdpater_Talk mAdapter;
    List<MyComment> mList = new ArrayList<MyComment>();

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
                } else {
                    showMsg(msg);
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();
                //showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commnet_viewCallback(boolean isCache, Response response) {
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

    public void bindData(List<MyComment> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        mList.addAll(0, list);

//        mList.addAll(list);
        mAdapter = new ListViewAdpater_Talk(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法


        if (page == 1)
            xListView.setSelection(xListView.getBottom());
        else {
            mAdapter.notifyDataSetChanged();
            xListView.setAdapter(mAdapter);
        }

        //xListView.notify();


    }


    /**
     * 回复
     *
     * @param view
     */
    @Event(value = R.id.ll_reply)
    private void ll_replyClick(View view) {
        String et_content = mHolder.getViewText(R.id.et_content);
        if (CommFunAndroid.isNullOrEmpty(et_content)) {
            showMsg("你还没有输入内容哦~");
            return;
        }

        int pid = 0;

        if (mList != null && !mList.isEmpty()) {
            MyComment comment = mList.get(mList.size() - 1);
            if (comment != null) {
                pid = comment.getId();
            }
        }

        mPresenter.store_comment(mUid, 2, et_content, pid);

    }


    @Override
    public void store_commentCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mHolder.setText(R.id.et_content, "");
                    page = 1;
                    initData(page, pageSize);
                } else {
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}