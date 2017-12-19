package com.ysy15350.startcarunion.main_tabs;

import java.util.List;

import api.base.model.Response;
import api.model.Banner;

public interface MainTab1ViewInterface {

    /**
     * 绑定banner
     *
     * @param actives
     */
    public void bindBanner(List<Banner> banners);

    /**
     * 绑定banner
     *
     * @param isCache
     * @param response
     */
    public void home_banaerCallback(boolean isCache, Response response);

    /**
     * 首页欢迎语
     *
     * @param isCache
     * @param response
     */
    public void qd_welcomeCallback(boolean isCache, Response response);

    /**
     * 签到
     *
     * @param isCache
     * @param response
     */
    public void store_signCallback(boolean isCache, Response response);

}
