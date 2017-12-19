package com.ysy15350.startcarunion.mine.my_collection;

import api.base.model.Response;

public interface MyCollectionViewInterface {


    public void coll_listCallback(boolean isCache, Response response);

    public void collectionCallback(boolean isCache, Response response);

    public void make_tellCallback(boolean isCache, Response response);


}
