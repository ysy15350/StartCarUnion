package com.ysy15350.startcarunion.main_tabs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_Message;
import com.ysy15350.startcarunion.business.TalkActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.Message;
import base.BaseData;
import base.mvp.MVPBaseListViewFragment;
import common.CommFunAndroid;
import common.JsonConvertor;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_list)
public class MainTab2Fragment extends MVPBaseListViewFragment<MainTab2ViewInterface, MainTab2Presenter>
        implements MainTab2ViewInterface {

    public MainTab2Fragment() {
    }

    @Override
    public MainTab2Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab2Presenter(getActivity());
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        setTitle("留言列表");
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
        //CommFunMessage.mContext = getActivity();
        //CommFunMessage.showWaitDialog("数据加载中...");

//        LoadingDialog dialog = new LoadingDialog(mContext);
//        dialog.show();


        try {
            String messageListJson = BaseData.getCache("messageList");

            if (!CommFunAndroid.isNullOrEmpty(messageListJson)) {

                List<Message> list = JsonConvertor.fromJson(messageListJson, new TypeToken<List<Message>>() {
                }.getType());
                bindData(list);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPresenter.comment_reply(page, pageSize);
    }

    ListViewAdpater_Message mAdapter;
    List<Message> mList = new ArrayList<Message>();

    @Override
    public void comment_replyCallback(boolean isCache, Response response) {

        //CommFunMessage.hideWaitDialog();

        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("messageList", response.getResultJson());
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

    public void bindData(List<Message> list) {


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
        mAdapter = new ListViewAdpater_Message(mContext, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法


        if (list != null || !list.isEmpty()) {
            xListView.notify();
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
                int msg_id = message.getId();

                if (msg_id != 0) {

                    int uid = BaseData.getInstance().getUid();

                    if (uid != msg_id) {

                        Intent intent = new Intent(mContext, TalkActivity.class);


                        intent.putExtra("id", msg_id);
                        //intent.putExtra("pid", mStoreInfo.getPid());

                        startActivity(intent);
                    }
                }
            }
        }
    }
}
