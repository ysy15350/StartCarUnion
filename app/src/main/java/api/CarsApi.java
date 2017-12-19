package api;

import api.base.server.ApiCallBack;

public interface CarsApi {

    /**
     * 获取车型
     */
    public void pro_type(int pid, ApiCallBack callBack);

    /**
     * 所有品牌类型
     *
     * @param callBack
     */
    public void type_list(int page, int limit, ApiCallBack callBack);

    /**
     * 询价列表
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void inquiry_list(int page, int limit, ApiCallBack callBack);


    /**
     * 发布询价，回复询价
     *
     * @param pid      上级ID
     * @param sid      商家ID
     * @param atlas    图集ID，多张以“,”分隔
     * @param content  询价内容
     * @param callBack
     */
    public void add_inquiry(int pid, String atlas, String content, ApiCallBack callBack);


    /**
     * 询价详情
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void inquiry_info(int qid, ApiCallBack callBack);


    /**
     * 我的询价列表
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void use_inquiry_info(int page, int limit, ApiCallBack callBack);

}
