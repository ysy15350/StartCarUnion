package com.ysy15350.startcarunion.ask_price_platform;

import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class AskPriceDetailPresenter extends BasePresenter<AskPriceDetailViewInterface> {

    public AskPriceDetailPresenter(Context context) {
        super(context);

    }

    CarsApi carsApi = new CarsApiImpl();

    public void inquiry_info(int qid) {
        carsApi.inquiry_info(qid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.inquiry_infoCallback(isCache, response);
            }
        });
    }


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
