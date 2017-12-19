package com.ysy15350.startcarunion.ask_price_platform.tabs;


import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class AskTab3Presenter extends BasePresenter<AskTab3ViewInterface> {

    public AskTab3Presenter() {
    }

    public AskTab3Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    CarsApi carsApi = new CarsApiImpl();

    public void use_inquiry_info(int page, int pageSize) {
        carsApi.use_inquiry_info(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                mView.inquiry_infoCallback(isCache, response);

            }
        });
    }


}
