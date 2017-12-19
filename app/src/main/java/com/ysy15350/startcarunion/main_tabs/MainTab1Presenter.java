package com.ysy15350.startcarunion.main_tabs;


import android.content.Context;

import api.IndexApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.IndexApiImpl;
import base.mvp.BasePresenter;

public class MainTab1Presenter extends BasePresenter<MainTab1ViewInterface> {

    public MainTab1Presenter() {
    }

    public MainTab1Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    IndexApi indexApi = new IndexApiImpl();


    /**
     * 获取banner
     */
    public void getBanner() {

        indexApi.home_banaer(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.home_banaerCallback(isCache, response);
            }
        });


    }

    /**
     * 首页公告语
     */
    public void qd_welcome() {
        indexApi.qd_welcome(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.qd_welcomeCallback(isCache, response);
            }
        });
    }

    /**
     * 签到
     */
    public void store_sign() {
        indexApi.store_sign(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_signCallback(isCache, response);
            }
        });
    }

}
