package com.ysy15350.startcarunion.store;

import android.content.Context;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class CityListPresenter extends BasePresenter<CityListViewInterface> {

    public CityListPresenter(Context context) {
        super(context);

    }

    StoreApi storeApi = new StoreApiImpl();


    public void city_list(int page, int pageSize) {
        storeApi.city_list(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.city_listCallback(isCache, response);
            }
        });
    }

}
