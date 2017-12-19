package com.ysy15350.startcarunion.mine.my_msg;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyMsg;
import com.ysy15350.startcarunion.business.TalkActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.Message;
import base.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

@ContentView(R.layout.activity_list)
public class MyMsgActivity extends MVPBaseListViewActivity<MyMsgViewInterface, MyMsgPresenter>
        implements MyMsgViewInterface {

    @Override
    protected MyMsgPresenter createPresenter() {

        return new MyMsgPresenter(MyMsgActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));

        setFormHead("我的消息");

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
        mPresenter.message_list(page, pageSize);
    }

    @Override
    public void message_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mHolder.setText(R.id.tv_score, response.getPoint() + "");
                    List<Message> list = response.getData(new TypeToken<List<Message>>() {
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

    ListViewAdpater_MyMsg mAdapter;
    List<Message> mList = new ArrayList<Message>();

    public void bindData(List<Message> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        mList.addAll(list);
        mAdapter = new ListViewAdpater_MyMsg(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Object item = parent.getItemAtPosition(position);
        if (item != null) {
            if (item instanceof Message) {
                Message message = (Message) item;
                int msg_id = message.getReturn_id();

                if (msg_id != 0) {

                    int uid = BaseData.getInstance().getUid();

                    if (uid != msg_id) {

                        Intent intent = new Intent(this, TalkActivity.class);


                        intent.putExtra("id", msg_id);
                        //intent.putExtra("pid", mStoreInfo.getPid());

                        startActivity(intent);
                    }
                }
            }
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
