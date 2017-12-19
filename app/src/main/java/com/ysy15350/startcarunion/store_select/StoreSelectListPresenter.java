package com.ysy15350.startcarunion.store_select;

import android.content.Context;

import api.CarsApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.CarsApiImpl;
import base.mvp.BasePresenter;

public class StoreSelectListPresenter extends BasePresenter<StoreSelectListViewInterface> {

    public StoreSelectListPresenter(Context context) {
        super(context);

    }

    CarsApi carsApi = new CarsApiImpl();

    /**
     * 获取顶级类型
     */
    public void type_list(int page, int pageSize) {
        carsApi.type_list(page, pageSize, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                // TODO Auto-generated method stub
                mView.bindCarType(response);
            }
        });

    }

}
