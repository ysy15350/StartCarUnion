package com.ysy15350.startcarunion.business;

import android.content.Context;

import api.MemberApi;
import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class StoreInfoDetailPresenter extends BasePresenter<StoreInfoDetailViewInterface> {

    public StoreInfoDetailPresenter(Context context) {
        super(context);

    }


    MemberApi memberApi = new MemberApiImpl();
    StoreApi storeApi = new StoreApiImpl();

    public void store_info(int uid) {


        memberApi.store_info(uid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_infoCallback(isCache, response);
            }
        });
    }


    /**
     * 收藏
     *
     * @param sid
     */
    public void collection(int sid) {
        storeApi.collection(sid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.collectionCallback(isCache, response);
            }
        });
    }

    public void make_tell(int sid, String tell) {
        storeApi.make_tell(sid, tell, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.make_tellCallback(isCache, response);
            }
        });
    }

    /**
     * 添加商家浏览记录
     * @param sid
     */
    public  void store_browse(int sid){
        storeApi.store_browse(sid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
            }
        });
    }


}
