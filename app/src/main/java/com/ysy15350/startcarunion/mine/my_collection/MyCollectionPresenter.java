package com.ysy15350.startcarunion.mine.my_collection;

import android.content.Context;

import api.MemberApi;
import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import api.impl.StoreApiImpl;
import base.mvp.BasePresenter;

public class MyCollectionPresenter extends BasePresenter<MyCollectionViewInterface> {

    public MyCollectionPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();
    StoreApi storeApi = new StoreApiImpl();

    public void coll_list(int page, int pageSize) {
        memberApi.coll_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.coll_listCallback(isCache, response);
            }
        });
    }

    /**
     * 收藏
     *
     * @param sid
     */
    public void collection(int sid) {
        storeApi.collection(sid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.collectionCallback(isCache, response);
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
