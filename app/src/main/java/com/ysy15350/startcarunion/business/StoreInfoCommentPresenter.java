package com.ysy15350.startcarunion.business;

import android.content.Context;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class StoreInfoCommentPresenter extends BasePresenter<StoreInfoCommentViewInterface> {

    public StoreInfoCommentPresenter(Context context) {
        super(context);

    }

    StoreApi storeApi = new StoreApiImpl();

    public void store_comment(int sid, int type, String content, int pid) {
        storeApi.store_comment(sid, type, content, pid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_commentCallback(isCache, response);
            }
        });
    }

}
