package com.ysy15350.startcarunion.mine.my_comment;

import android.content.Context;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class MyCommentPresenter extends BasePresenter<MyCommentViewInterface> {

    public MyCommentPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();


    public void comment_list(int page, int pageSize) {
        memberApi.comment_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.comment_listCallback(isCache, response);
            }
        });
    }

}
