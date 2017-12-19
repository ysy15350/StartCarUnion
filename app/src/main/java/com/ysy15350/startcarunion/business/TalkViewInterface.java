package com.ysy15350.startcarunion.business;

import api.base.model.Response;

public interface TalkViewInterface {

    public void comment_listCallback(boolean isCache, Response response);

    public void store_commentCallback(boolean isCache, Response response);

    public void commnet_viewCallback(boolean isCache, Response response);
}
