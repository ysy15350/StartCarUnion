package com.ysy15350.startcarunion.business;

import api.base.model.Response;

public interface StoreInfoListViewInterface {

    public void store_listCallback(boolean isCache, Response response);

    public void make_tellCallback(boolean isCache, Response response);

}
