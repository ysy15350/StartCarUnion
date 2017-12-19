package api;

import api.base.server.ApiCallBack;

public interface StoreApi {

    /**
     * 商家列表
     *
     * @param pid
     * @param keywords
     * @param page
     * @param limit
     * @param callBack
     */
    public void store_list(int flag, int pid, int city_id, String keywords, int page, int limit, ApiCallBack callBack);

    /**
     * 收藏、取消收藏商家
     *
     * @param sid
     * @param callBack
     */
    public void collection(int sid, ApiCallBack callBack);

    /**
     * 添加评论、留言接口
     *
     * @param sid      被评论、留言商家ID
     * @param type     类型，1:评论  2:留言
     * @param content
     * @param pid      上级ID,可不填
     * @param callBack
     */
    public void store_comment(int sid, int type, String content, int pid, ApiCallBack callBack);

    /**
     * 留言列表
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void comment_list(int sid, int page, int limit, ApiCallBack callBack);

    /**
     * 添加拨打电话记录
     *
     * @param sid
     * @param tell
     * @param callBack
     */
    public void make_tell(int sid, String tell, ApiCallBack callBack);

    /**
     * 添加商家浏览记录
     *
     * @param sid
     * @param callBack
     */
    public void store_browse(int sid, ApiCallBack callBack);


    /**
     * 获取城市列表
     *
     * @param callBack
     */
    public void city_list(ApiCallBack callBack);

    /**
     * 添加联系人
     *
     * @param tell
     * @param callBack
     */
    public void contacts(String tell, ApiCallBack callBack);

    /**
     * 联系人列表
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void contactslist(int page, int limit, ApiCallBack callBack);

    /**
     * 留言列表接口
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void comment_reply(int page, int limit, ApiCallBack callBack);

    /**
     * 设置留言状态为已浏览状态接口
     *
     * @param sid
     * @param callBack
     */
    public void comment_view(int sid, ApiCallBack callBack);

}
