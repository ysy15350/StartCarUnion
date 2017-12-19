package com.ysy15350.startcarunion.store;

import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class CarTypePresenter extends BasePresenter<CarTypeViewInterface> {

    public CarTypePresenter(Context context) {
        super(context);

    }

    CarsApi carsApi = new CarsApiImpl();

    /**
     * 获取顶级类型
     */
    public void pro_type(int pid) {
        carsApi.pro_type(pid, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                // TODO Auto-generated method stub
                mView.bindTopType(response);
            }

            @Override
            public void onSuccess(boolean isCache, String data) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailed(String msg) {
                // TODO Auto-generated method stub

            }
        });

    }


}
