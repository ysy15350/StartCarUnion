package com.ysy15350.startcarunion.mine.user_center;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class UserCenterPresenter extends BasePresenter<UserCenterViewInterface> {

    public UserCenterPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    public void user_info() {
        memberApi.user_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache,response);
                mView.user_infoCallback(isCache,response);
            }
        });
    }

}
