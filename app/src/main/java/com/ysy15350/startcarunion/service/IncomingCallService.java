package com.ysy15350.startcarunion.service;

/**
 * Created by yangshiyou on 2017/9/6.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import api.StoreApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.StoreApiImpl;
import common.CommFunMessage;

/**
 * 获取来电号码服务
 */
public class IncomingCallService extends Service {

    /**
     * 电话服务管理器
     */
    private TelephonyManager telephonyManager;

    /**
     * 电话状态监听器
     */
    private MyPhoneStateListener myPhoneStateListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取来电号码
        getIncomingCall();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 不获取来电号码
        getIncomingCallCancel();
    }

    /**
     * 获取来电号码
     */
    private void getIncomingCall() {
        // 获取电话系统服务
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        myPhoneStateListener = new MyPhoneStateListener();
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 不获取来电号码
     */
    private void getIncomingCallCancel() {
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    /**
     * 电话状态监听器
     */
    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                // 如果电话铃响
                case TelephonyManager.CALL_STATE_RINGING:
//                    Toast.makeText(IncomingCallService.this, "来电号码是：" + incomingNumber, Toast
//                            .LENGTH_LONG).show();
                    addContact(incomingNumber);
                    break;
            }
        }
    }

    private void addContact(String phone) {

        StoreApi storeApi = new StoreApiImpl();
        storeApi.contacts(phone, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    CommFunMessage.showMsgBox(msg);
                }
            }
        });
    }
}
