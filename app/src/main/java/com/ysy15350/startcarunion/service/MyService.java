package com.ysy15350.startcarunion.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import api.PublicApi;
import api.base.model.Config;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.PublicApiImpl;
import base.BaseData;
import base.config.BaseInfoDao;
import base.config.entity.BaseInfo;
import common.CommFunAndroid;

import static java.lang.System.currentTimeMillis;

/**
 * 后台服务，定时更新token
 *
 * @author yangshiyou
 */
public class MyService extends Service {

    private static final String TAG = "MyService";

    PublicApi publicApi = new PublicApiImpl();

    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("myservice", "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer != null)
            timer.cancel();
        timer = new Timer();

        if (task != null)
            task.cancel();

        task = new TimerTask() {

            @Override
            public void run() {


                // ----------更新token---------------------------
                long currentTime = currentTimeMillis();

                String token = "";
                long tokenTime = 0;


                BaseInfo baseInfo = BaseInfoDao.getInstance().getBaseInfo();

                try {
                    if (baseInfo != null) {
                        token = baseInfo.getToken();
                        tokenTime = baseInfo.getTokenCreateTime();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                long differTime = currentTime - tokenTime;
                if (CommFunAndroid.isNullOrEmpty(token) || differTime > 3600 * 1000) {
                    getToken();
                }


                mHandler.sendEmptyMessage(0);
            }
        };

        timer.schedule(task, 0, Config.getHeartBeatTime());
        return super.onStartCommand(intent, flags, startId);
    }


    private void getToken() {
        String className = this.getClass().getName();
        publicApi.getToken(className, new ApiCallBack() {
            public void onSuccess(boolean isCache, Response response) {
                if (response != null) {
                    int code = response.getCode();
                    if (code == 200) {
                        String token = response.getToken();
                        if (!CommFunAndroid.isNullOrEmpty(token)) {
                            BaseData.setToken(token);
                        }
                    } else {
                        getToken();
                    }
                }
            }

            ;
        });// 获取token值
    }


    @Override
    public void onDestroy() {

        if (timer != null && task != null) {
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }

        super.onDestroy();
    }

    Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:

                    break;
            }
        }
    };

}
