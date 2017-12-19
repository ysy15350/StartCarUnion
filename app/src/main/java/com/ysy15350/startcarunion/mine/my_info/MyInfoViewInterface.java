package com.ysy15350.startcarunion.mine.my_info;

import api.base.model.Response;

public interface MyInfoViewInterface {

    public void user_infoCallback(boolean isCache, Response response);


    /**
     * 上传头像回调
     *
     * @param response
     */
    public void uppictureCallback(Response response);

    public void save_infoCallback(Response response);

}
