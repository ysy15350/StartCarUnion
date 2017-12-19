package api;

import java.io.File;

import api.base.server.ApiCallBack;

public interface MemberApi {


    /**
     * 上传文件
     *
     * @param file
     * @param callBack
     */
    public void uppicture(File file, ApiCallBack callBack);

    /**
     * 个人资料
     *
     * @param uid
     * @param callBack
     */
    public void user_info(ApiCallBack callBack);

    /**
     * 修改商家个人资料
     *
     * @param field    修改商家信息的字段名   icon:图片ID
     *                 fullname:公司名称
     *                 nickname:昵称
     * @param value
     * @param callBack
     */
    public void save_info(String field, String value, ApiCallBack callBack);

    /**
     * 修改手机号
     *
     * @param password
     * @param mobile
     * @param mobile_code
     * @param callBack
     */
    public void save_mobile(String password, String mobile, String mobile_code, ApiCallBack callBack);

    /**
     * 找回密码
     *
     * @param password
     * @param mobile
     * @param mobile_code
     * @param callBack
     */
    public void save_password(String password, String mobile, String mobile_code, ApiCallBack callBack);


    /**
     * 获取商家店铺信息
     *
     * @param callBack
     */
    public void store_info(int uid, ApiCallBack callBack);

    /**
     * 更新商家店铺信息
     *
     * @param ad_img   店招图片ID,多张以”,”分隔
     * @param tent     店铺简介
     * @param tell     联系方式
     * @param address  店铺地址
     * @param qq
     * @param email
     * @param brand    品牌ID,多个“@”分隔
     * @param atlas    店铺图集ID,多张以”,”分隔
     * @param callBack
     */
    public void save_store_info(String ad_img, String tent, String tell, int city_id, String address, String qq, String email, String brand, String atlas,String type_info, ApiCallBack callBack);


    /**
     * 获取电话号码列表
     *
     * @param callBack
     */
    public void store_tell(int type,ApiCallBack callBack);

    /**
     * 添加或删除
     *
     * @param tell
     * @param type     1:添加   2:删除
     * @param callBack
     */
    public void add_store_tell(String tell, int type,int mobile_type, ApiCallBack callBack);

    /**
     * 我的收藏
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void coll_list(int page, int limit, ApiCallBack callBack);

    /**
     * 我的评论
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void comment_list(int page, int limit, ApiCallBack callBack);

    /**
     * 签到记录
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void sign_list(int page, int limit, ApiCallBack callBack);

    /**
     * 电话拨打记录
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void dial_record(int page, int limit, ApiCallBack callBack);

    /**
     * 我的访客，我的足迹
     *
     * @param type     1：我的访客；2：我的足迹
     * @param page
     * @param limit
     * @param callBack
     */
    public void visitors_list(int type, int page, int limit, ApiCallBack callBack);

    /**
     * 我的消息列表
     *
     * @param page
     * @param limit
     * @param callBack
     */
    public void message_list(int page, int limit, ApiCallBack callBack);

    /**
     * 短信发送接口
     *
     * @param password
     * @param mobile
     * @param type     savemobile:修改手机号 savepass：找回密码
     * @param callBack
     */
    public void send_mobile_code(String password, String mobile, String type, ApiCallBack callBack);


}
