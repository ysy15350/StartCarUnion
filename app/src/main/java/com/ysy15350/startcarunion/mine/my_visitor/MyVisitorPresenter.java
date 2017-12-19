package com.ysy15350.startcarunion.mine.my_visitor;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyVisitorPresenter extends BasePresenter<MyVisitorViewInterface> {

    public MyVisitorPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    public void visitors_list(int page, int pageSize) {
        memberApi.visitors_list(1,page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.visitors_listCallback(isCache, response);
            }
        });
    }

}
