package com.ysy15350.startcarunion.mine.my_contacts;

import android.content.Context;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class MyContactsPresenter extends BasePresenter<MyContactsViewInterface> {

    public MyContactsPresenter(Context context) {
        super(context);

    }

    StoreApi storeApi = new StoreApiImpl();

    public void contactslist(int page, int pageSize) {
        storeApi.contactslist(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.contactslistCallback(isCache, response);
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
