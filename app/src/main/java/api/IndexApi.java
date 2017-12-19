package api;

import api.base.server.ApiCallBack;

public interface IndexApi {


    /**
     * 首页Banner
     *
     * @param callBack
     */
    public void home_banaer(ApiCallBack callBack);

    /**
     * 首页公告语
     *
     * @param callBack
     */
    public void qd_welcome(ApiCallBack callBack);

    /**
     * 积分签到
     * @param callBack
     */
    public void store_sign(ApiCallBack callBack);

    /**
     * 交易担保二维码
     * @param callBack
     */
    public void guarantee_code(ApiCallBack callBack);

}
