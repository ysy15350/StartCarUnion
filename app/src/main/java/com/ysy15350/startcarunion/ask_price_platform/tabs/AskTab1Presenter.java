package com.ysy15350.startcarunion.ask_price_platform.tabs;


import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class AskTab1Presenter extends BasePresenter<AskTab1ViewInterface> {

    public AskTab1Presenter() {
    }

    public AskTab1Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    CarsApi carsApi = new CarsApiImpl();

    public void inquiry_list(int page, int pageSize) {
        carsApi.inquiry_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                mView.inquiry_listCallback(isCache, response);

            }
        });
    }


}
