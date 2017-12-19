package com.ysy15350.startcarunion.business;

import api.base.model.Response;

public interface StoreInfoDetailViewInterface {

    public void store_infoCallback(boolean isCache, Response response);


    public void collectionCallback(boolean isCache, Response response);

    public void make_tellCallback(boolean isCache, Response response);

    public void store_browseCallback(boolean isCache, Response response);

}
