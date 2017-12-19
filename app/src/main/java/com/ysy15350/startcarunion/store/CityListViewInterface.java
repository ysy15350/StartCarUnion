package com.ysy15350.startcarunion.store;

import api.base.model.Response;

public interface CityListViewInterface {

    /**
     * 城市列表
     *
     * @param response
     */
    public void city_listCallback(boolean isCache, Response response);
}
