package com.ysy15350.startcarunion.mine.my_shop;

import android.content.Context;

import java.io.File;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.BaseData;
import base.mvp.BasePresenter;

public class EditShopPresenter extends BasePresenter<EditShopViewInterface> {

    public EditShopPresenter(Context context) {
        super(context);

    }


    MemberApi memberApi = new MemberApiImpl();

    public void store_info() {

        int uid = BaseData.getInstance().getUid();

        memberApi.store_info(uid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_infoCallback(isCache, response);
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


    public void save_store_info(String ad_img, String tent, String tell, int city_id, String address, String qq,
                                String email, String brand, String atlas, String type_info) {

        memberApi.save_store_info(ad_img, tent, tell, city_id, address, qq, email, brand, atlas, type_info, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.save_store_infoCallback(response);
            }
        });

    }


}
