package com.ysy15350.startcarunion.mine.my_call;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyCallPresenter extends BasePresenter<MyCallViewInterface> {

    public MyCallPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();


    public void dial_record(int page, int pageSize) {
        memberApi.dial_record(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.dial_recordCallback(isCache, response);
            }
        });
    }

}
