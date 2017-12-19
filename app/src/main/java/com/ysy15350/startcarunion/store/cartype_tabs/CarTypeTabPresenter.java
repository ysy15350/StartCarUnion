package com.ysy15350.startcarunion.store.cartype_tabs;


import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class CarTypeTabPresenter extends BasePresenter<CarTypeTabViewInterface> {

    public CarTypeTabPresenter() {
    }

    public CarTypeTabPresenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    CarsApi carsApi = new CarsApiImpl();

    /**
     * 获取车辆类型列表（绑定gridview）
     *
     * @param pid
     */
    public void getCarTypeList(int pid) {
        carsApi.pro_type(pid, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                // TODO Auto-generated method stub
                mView.bindCarType(response);
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
