package com.ysy15350.startcarunion.mine.my_footprint;

import api.base.model.Response;

public interface MyFootprintViewInterface {

    public void visitors_listCallback(boolean isCache, Response response);

    public void make_tellCallback(boolean isCache, Response response);
}
