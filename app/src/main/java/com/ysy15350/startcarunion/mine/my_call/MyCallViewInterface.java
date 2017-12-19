package com.ysy15350.startcarunion.mine.my_call;

import api.base.model.Response;

public interface MyCallViewInterface {

    /**
     * 电话拨打记录
     *
     * @param response
     */
    public void dial_recordCallback(boolean isCache, Response response);
}
