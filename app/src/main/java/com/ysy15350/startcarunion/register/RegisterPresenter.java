package com.ysy15350.startcarunion.register;

import android.content.Context;

import java.io.File;

import api.MemberApi;
import api.PublicApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterViewInterface> {

    public RegisterPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();
    MemberApi memberApi = new MemberApiImpl();

    public void register(int icon, String mobile, String fullname, String password, String address, int type,
                         String verification_code) {

        publicApi.register(icon, mobile, fullname, password, address, type, verification_code, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.registerCallback(response);
            }
        });

    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void uppicture(File file) {
        memberApi.uppicture(file, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                //{"code":200,"pic":"\/Uploads\/Picture\/2017-08-31\/59a7a5689e7f9.jpg","id":2}
                if (response != null)
                    mView.uppictureCallback(response);
            }


        });
    }


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

}
