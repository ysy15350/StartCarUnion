package com.ysy15350.startcarunion.mine.my_contacts;

import api.base.model.Response;

public interface MyContactsViewInterface {
    /**
     * 联系人列表
     *
     * @param isCache
     * @param response
     */
    public void contactslistCallback(boolean isCache, Response response);

    public void make_tellCallback(boolean isCache, Response response);
}
