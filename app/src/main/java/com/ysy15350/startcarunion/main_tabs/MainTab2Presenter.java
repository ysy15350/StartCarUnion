package com.ysy15350.startcarunion.main_tabs;


import android.content.Context;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class MainTab2Presenter extends BasePresenter<MainTab2ViewInterface> {

    public MainTab2Presenter() {
    }

    public MainTab2Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    StoreApi storeApi = new StoreApiImpl();

//    public void comment_list(int page, int pageSize) {
//        storeApi.comment_list(0, page, pageSize, new ApiCallBack() {
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                super.onSuccess(isCache, response);
//                mView.comment_listCallback(isCache, response);
//            }
//        });
//    }

    public void comment_reply(int page, int pageSize) {
        storeApi.comment_reply(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.comment_replyCallback(isCache, response);
            }
        });
    }


}
