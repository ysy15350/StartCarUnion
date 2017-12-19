package com.ysy15350.startcarunion.ask_price_platform;

import api.base.model.Response;

public interface AskPriceDetailViewInterface {

    public void inquiry_infoCallback(boolean isCache, Response response);

    public void add_inquiryCallback(boolean isCache, Response response);

}
