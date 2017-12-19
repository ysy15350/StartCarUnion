package com.ysy15350.startcarunion.mine.my_score;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyScorePresenter extends BasePresenter<MyScoreViewInterface> {

    public MyScorePresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();


    public void sign_list(int page, int pageSize) {
        memberApi.sign_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.sign_listCallback(isCache, response);
            }
        });
    }


}
