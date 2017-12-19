package base;

import android.content.Context;

import java.io.ObjectStreamException;

import base.config.BaseInfoDao;
import base.config.UserHelper;
import base.config.entity.BaseInfo;
import base.config.entity.UserInfo;
import common.cache.ACache;

public class BaseData {


    private static ACache aCache;


    /**
     * 是否登录
     */
    private boolean isLogin = false;

    String token;

    /**
     * 是否有网络
     */
    public static boolean isNetwork;

    private final static String TAG = "BaseData";

    private BaseData() {
    }

    public static BaseData getInstance() {
        return BaseDataHolder.sInstance;
    }

    public static BaseData getInstance(Context context) {
        init(context);
        return BaseDataHolder.sInstance;
    }

    private static class BaseDataHolder {
        private static final BaseData sInstance = new BaseData();
    }

    // 杜绝单例对象在反序列化时重新生成对象
    private Object readResolve() throws ObjectStreamException {
        return BaseDataHolder.sInstance;
    }

    private static void init(Context context) {
        if (aCache == null && context != null) {
            aCache = ACache.get(context);
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {

        BaseInfo baseInfo = BaseInfoDao.getInstance().getBaseInfo();
        if (baseInfo != null) {
            String token = baseInfo.getToken();
            if (token == null) {
                token = "";
            }
            return token;
        }
        return "";

    }


    /**
     * 设置token
     *
     * @param token
     */
    public static void setToken(String token) {
        BaseInfoDao.getInstance().setToken(token);
    }


    /**
     * 获取当前用户登录信息
     *
     * @return
     */
    public UserInfo getUserInfo() {

        try {
            int uid = getUid();
            UserInfo userInfo = UserHelper.getInstance().getUserInfoByUid(uid);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 缓存已登录用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {

        try {
            //UserHelper.getInstance().delete();
            if (userInfo != null)
                userInfo.setUid(getUid());
            UserHelper.getInstance().saveOrUpdate(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置UID
     *
     * @param uid
     */
    public void setUid(int uid) {
        BaseInfoDao.getInstance().setUid(uid);
    }

    /**
     * 获取Uid
     *
     * @return
     */
    public int getUid() {
        BaseInfo baseInfo = BaseInfoDao.getInstance().getBaseInfo();
        if (baseInfo != null) {
            int uid = baseInfo.getUid();

            return uid;
        }
        return 0;
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value) {
        if (aCache != null && value != null) {
            aCache.put(key, value);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value, int time) {
        if (aCache != null && value != null) {
            aCache.put(key, value, time);
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static String getCache(String key) {
        if (aCache != null)
            return aCache.getAsString(key);
        return "";
    }

}