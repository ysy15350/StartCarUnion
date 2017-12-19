package com.ysy15350.startcarunion;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import org.xutils.x;

import base.BaseData;
import base.config.ConfigHelper;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.CrashHandler;

import static android.R.attr.level;


/**
 * Created by yangshiyou on 2016/11/23.
 */

public class MyApplication extends Application {

    //Android 各种坑
    //http://blog.csdn.net/cjpx00008/article/details/52100755

    public static Context applicationContext;

    private static final String TAG = "MyApplication";

    public MyApplication getInstance() {
        return (MyApplication) getApplicationContext();
    }

    public static Context getContext() {
        applicationContext = MyApplication.getContext();
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //FileUtils.writeActivityLog(">>>>>>>>>>>>>>>>>MyApplication   onCreate>>>>>>>>>>>>>>>" + this.toString());

        Log.i(TAG, this.toString() + ">>>>>>>>>>>>onCreate" + ">>>>>>>>>>");

        registerActivityLifecycleCallbacks();

        initUtils();//初始化工具类

        initData();//初始化数据，加载配置等

        //initService();//初始化服务
    }


    private void initUtils() {

        // 网址：https://github.com/wyouflf/xUtils3
        // 参考博客：http://blog.csdn.net/tyk9999tyk/article/details/53306035

        //xUtils缓存目录:/data/user/0/com.ysy15350.startcarunion/databases/xUtils_http_cookie.db  (2)(3)

        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        //------------------闪退错误捕获，记录日志位置：/aandroid_log/
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        //----------------------------------

        BaseData.getInstance(this);
        CommFunAndroid.mContext = getApplicationContext();
        CommFunMessage.mContext = getApplicationContext();

    }

//    PublicApi publicApi = new PublicApiImpl();

    private void initData() {

        ConfigHelper.initConfigInfo();//初始化配置信息


//        publicApi.getToken(this.getClass().getName(), new ApiCallBack() {
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                super.onSuccess(isCache, response);
//                if (response != null) {
//
//                    int code = response.getCode();
//                    String msg = response.getMessage();
//                    if (code == 200) {
//                        String token = response.getToken();
//                        if (!CommFunAndroid.isNullOrEmpty(token)) {
//
//                            Config.setTokenRefreshTime(System.currentTimeMillis());
//                            BaseData.setToken(token);
//
//                        }
//                    }
//                }
//            }
//        });
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
        if (level == TRIM_MEMORY_UI_HIDDEN) {
//            isBackground = true;
//            notifyBackground();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    private void registerActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityCreated>>>>>>>>>>>>>>>" + activity.toString());

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityResumed>>>>>>>>>>>>>>>" + activity.toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivitySaveInstanceState>>>>>>>>>>>>>>>" + activity.toString());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityDestroyed>>>>>>>>>>>>>>>" + activity.toString());
            }
        });
    }


}

