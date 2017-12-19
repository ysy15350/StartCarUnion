package com.ysy15350.startcarunion.forgot_pwd;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class ForgotPwdPresenter extends BasePresenter<ForgotPwdViewInterface> {

    public ForgotPwdPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    /**
     * 发送验证码
     */
    public void send_mobile_code(String mobile) {


        memberApi.send_mobile_code("", mobile, "savepass", new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.send_mobile_codeCallback(isCache, response);
            }
        });
    }

    public void save_password(String password, String mobile, String mobile_code) {
        memberApi.save_password(password, mobile, mobile_code, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.save_passwordCallback(isCache, response);
            }
        });
    }

}
