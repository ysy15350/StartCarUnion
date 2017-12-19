package com.ysy15350.startcarunion.mine.my_footprint;

import android.content.Context;

import api.MemberApi;
import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class MyFootprintPresenter extends BasePresenter<MyFootprintViewInterface> {

    public MyFootprintPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();
    StoreApi storeApi = new StoreApiImpl();

    public void visitors_list(int page, int pageSize) {
        memberApi.visitors_list(2, page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.visitors_listCallback(isCache, response);
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
