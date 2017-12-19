package com.ysy15350.startcarunion.mine.my_shop;

import api.base.model.Response;

public interface TellListViewInterface {


    /**
     * 电话列表
     *
     * @param isCache
     * @param response
     */
    public void store_tellCallback(boolean isCache, Response response);

    /**
     * 添加或删除电话
     *
     * @param isCache
     * @param response
     */
    public void add_store_tellCallback(boolean isCache, Response response);
}
