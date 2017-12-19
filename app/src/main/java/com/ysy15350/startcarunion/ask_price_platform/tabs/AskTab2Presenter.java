package com.ysy15350.startcarunion.ask_price_platform.tabs;


import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class AskTab2Presenter extends BasePresenter<AskTab2ViewInterface> {

    public AskTab2Presenter() {
    }

    public AskTab2Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    CarsApi carsApi = new CarsApiImpl();

    public void add_inquiry(int pid, String atlas, String content) {
        carsApi.add_inquiry(pid, atlas, content, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.add_inquiryCallback(isCache, response);
            }
        });
    }

}
