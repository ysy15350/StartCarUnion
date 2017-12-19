package api;

import api.base.server.ApiCallBack;

public interface PublicApi {

    /**
     * 获取token
     */
    public void getToken(String className, ApiCallBack callBack);

    /**
     * 注册
     *
     * @param icon              图片ID
     * @param mobile            手机号
     * @param fullname          公司名称
     * @param password          密码
     * @param address           公司地址
     * @param type              1:商家 2:经销商
     * @param verification_code 短信验证码
     * @param callBack
     */
    public void register(int icon, String mobile, String fullname, String password, String address, int type,
                         String verification_code, ApiCallBack callBack);

    /**
     * 会员登录
     *
     * @param account
     * @param password
     * @param callBack
     */
    public void login(String account, String password, ApiCallBack callBack);


    /**
     * 检查版本
     *
     * @param version_code
     *            版本号
     * @param callBack
     */
    public void checkVersion(int version_code, ApiCallBack callBack);


}
