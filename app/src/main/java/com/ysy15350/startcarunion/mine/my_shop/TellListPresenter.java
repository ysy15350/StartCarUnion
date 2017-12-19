package com.ysy15350.startcarunion.mine.my_shop;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class TellListPresenter extends BasePresenter<TellListViewInterface> {

    public TellListPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    /**
     * 获取电话列表
     */
    public void store_tell(int type) {
        memberApi.store_tell(type, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.store_tellCallback(isCache, response);
            }
        });
    }

    /**
     * 添加或删除号码
     *
     * @param tell
     * @param type 1:添加   2:删除
     */
    public void add_store_tell(String tell, int type, int mobile_type) {
        memberApi.add_store_tell(tell, type, mobile_type, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.add_store_tellCallback(isCache, response);
            }
        });
    }

    /**
     * 编辑手机号
     *
     * @param tell
     * @param newTell
     */
    public void update_store_tell(final String tell, final String newTell, final int type) {
        //先删除，再添加
        memberApi.add_store_tell(tell, 2, type, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                add_store_tell(newTell, 1, type);
            }
        });
    }

}
