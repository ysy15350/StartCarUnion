package com.ysy15350.startcarunion.mine.my_info;

import android.content.Context;

import java.io.File;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyInfoPresenter extends BasePresenter<MyInfoViewInterface> {

    public MyInfoPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    public void user_info() {
        memberApi.user_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.user_infoCallback(isCache,response);
            }
        });
    }


    public void save_info(String field,String value){
        memberApi.save_info(field, value, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.save_infoCallback(response);
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

}
