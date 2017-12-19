package com.ysy15350.startcarunion.login;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginViewInterface> {

    public LoginPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();

    /**
     * 用户登录
     *
     * @param account  用户名
     * @param password 密码
     */
    public void login(String account, String password) {
        publicApi.login(account, password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.userLoginCallBack(response);
            }
        });
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
