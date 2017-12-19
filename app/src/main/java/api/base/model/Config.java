package api.base.model;

public class Config {

    public final static boolean isDebug = false;

    // http://101.201.238.253:8080/yljy/sys/sysuser/login?phone=admin&password=123456
    /**
     * 网络连接类型；0:mobile(手机网路);1:WIFI;默认-1
     */
    private static int NETWORKCONNECTEDTYPE = -1;

    /**
     * 服务器地址
     */
    private static String server_url;

    /**
     * 服务器端口
     */
    private static int server_port;

    /**
     * 服务器项目名称
     */
    private static String projectName;


    /**
     * 设置调试模式；默认为调试模式
     *
     * @param isDebug
     */
    public static void setIsDebug(boolean isDebug) {


        if (isDebug) {// 如果是调试

            Config.server_url = "192.168.0.118";

            Config.server_port = 803;

            Config.projectName = "App";

        } else {// 正式

            Config.server_url = "qidian.59156.cn";

            Config.server_port = 80;

            Config.projectName = "App";

        }
    }

    public static String getServer_url() {
        setIsDebug(isDebug);
        return String.format("http://%s:%d/%s/", server_url, server_port, projectName);
    }

    public static String getServer() {
        setIsDebug(isDebug);
        return String.format("http://%s:%d/", server_url, server_port);
    }


    // "{\"account\":\"hhlvyou2017\",\"password\":\"hhlvyou_123456\"}");
    private static final String TOKEN_USERNAME = "qidian2017";// "huanghelvyou2017";//
    // token账号

    private static final String TOKEN_PASSWORD = "qidian_0829";// "batele_wel_passer";//
    // token密码

    private static final String AUTHORIZATION_PRE = "Qidian_";// 前缀

    //Qidian_asfsdfsdf

    /**
     * 第一次获取token传入
     */
    private static final String AUTHORIZATION = "header-token";// Gouxiang_header-token
    // ，header信息header_token


    /**
     * token有效时间
     */
    private static final int TOKEN_EFFECTIVE_TIME = 3600 * 1000;// 单位：毫秒

    /**
     * token最后更新时间，时间戳
     */
    private static long TOKEN_REFRESH_TIME;

    /**
     * 心跳时间(后台刷新时间)毫秒
     */
    private static long HeartBeatTime = 1000;

    /**
     * 登录心跳时间(毫秒)
     */
    private static long Login_HeartBeatTime = 60000;

    public static String getTokenUsername() {
        return TOKEN_USERNAME;
    }

    public static String getTokenPassword() {
        return TOKEN_PASSWORD;
    }

    public static String getAuthorizationPre() {
        return AUTHORIZATION_PRE;
    }

    public static String getAUTHORIZATION() {
        return AUTHORIZATION;
    }


    public static int getTokenEffectiveTime() {
        return TOKEN_EFFECTIVE_TIME;
    }

    public static long getTokenRefreshTime() {
        return TOKEN_REFRESH_TIME;
    }

    public static void setTokenRefreshTime(long tokenRefreshTime) {
        TOKEN_REFRESH_TIME = tokenRefreshTime;
    }

    public static long getHeartBeatTime() {
        return HeartBeatTime;
    }

    public static void setHeartBeatTime(long heartBeatTime) {
        HeartBeatTime = heartBeatTime;
    }

    public static long getLogin_HeartBeatTime() {
        return Login_HeartBeatTime;
    }

    public static void setLogin_HeartBeatTime(long login_HeartBeatTime) {
        Login_HeartBeatTime = login_HeartBeatTime;
    }
}
