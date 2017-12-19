package com.ysy15350.startcarunion.mine.my_msg;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyMsgPresenter extends BasePresenter<MyMsgViewInterface> {

    public MyMsgPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    public void message_list(int page, int pageSize) {
        memberApi.message_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.message_listCallback(isCache, response);
            }
        });
    }


}
