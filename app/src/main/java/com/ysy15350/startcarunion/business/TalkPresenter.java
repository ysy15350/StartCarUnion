package com.ysy15350.startcarunion.business;

import android.content.Context;

import api.MemberApi;
import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class TalkPresenter extends BasePresenter<TalkViewInterface> {

    public TalkPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();


    StoreApi storeApi = new StoreApiImpl();

    public void comment_list(int sid, int page, int pageSize) {
        if (sid == 0)
            return;
        storeApi.comment_list(sid, page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.comment_listCallback(isCache, response);
            }
        });
    }


    public void store_comment(int sid, int type, String content, int pid) {
        storeApi.store_comment(sid, type, content, pid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_commentCallback(isCache, response);
            }
        });
    }

    public void comment_view(int sid) {
        storeApi.comment_view(sid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.commnet_viewCallback(isCache, response);
            }
        });
    }

}
