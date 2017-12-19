package com.ysy15350.startcarunion.mine.my_shop;

import api.base.model.Response;

public interface EditShopViewInterface {

    public void store_infoCallback(boolean isCache, Response response);

    /**
     * 上传头像回调
     *
     * @param response
     */
    public void uppictureCallback(Response response);

    public void save_store_infoCallback(Response response);

}
