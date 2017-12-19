package com.ysy15350.startcarunion.business;

import android.content.Context;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class StoreInfoListPresenter extends BasePresenter<StoreInfoListViewInterface> {

    public StoreInfoListPresenter(Context context) {
        super(context);

    }

    StoreApi storeApi = new StoreApiImpl();

    public void store_list(int flag, int pid, int city_id, String keywords, int page, int limit) {
        storeApi.store_list(flag, pid, city_id, keywords, page, limit, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_listCallback(isCache, response);
            }
        });
    }

    public void make_tell(int sid, String tell) {
        storeApi.make_tell(sid, tell, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.make_tellCallback(isCache, response);
            }
        });
    }

}
