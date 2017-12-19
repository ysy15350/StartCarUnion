package com.ysy15350.startcarunion.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.BaseActivity;
import common.CommFunAndroid;
import common.CommFunMessage;

/**
 * 修改手机号
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_update_phone)
public class UpdatePhoneActivity extends BaseActivity {

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("修改手机号");
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        mHolder.setText(R.id.tv_phone, phone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (time_second_temp != 0) {// 如果时间没归零，继续从当前时间开始
            codeTimer();
        }
    }

    MemberApi memberApi = new MemberApiImpl();

    private void save_mobile(String password, String mobile, String mobile_code) {
        memberApi.save_mobile(password, mobile, mobile_code, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {
                        finish();
                    }
                    showMsg(msg);
                }
            }
        });
    }


    /**
     * 修改手机号
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        String phone = mHolder.getViewText(R.id.tv_phone);
        String password = mHolder.getViewText(R.id.et_pwd);
        String new_phone = mHolder.getViewText(R.id.et_phone);
        String code = mHolder.getViewText(R.id.et_code);

        if (CommFunAndroid.isNullOrEmpty(phone)) {
            showMsg("手机号获取失败");
            return;
        }

        if (CommFunAndroid.isNullOrEmpty(password)) {
            showMsg("请输入密码");
            return;
        }

        if (CommFunAndroid.isNullOrEmpty(new_phone)) {
            showMsg("请输入新手机号");
            return;
        }

        if (CommFunAndroid.isNullOrEmpty(code)) {
            showMsg("请输入验证码");
            return;
        }

        save_mobile(password, new_phone, code);

    }

    /**
     * 发送验证码
     */
    private void send_mobile_code() {
        String phone = mHolder.getViewText(R.id.tv_phone);
        String password = mHolder.getViewText(R.id.et_pwd);
        String new_phone = mHolder.getViewText(R.id.et_phone);

        if (CommFunAndroid.isNullOrEmpty(password)) {
            showMsg("请输入密码");
            return;
        }
        if (CommFunAndroid.isNullOrEmpty(new_phone)) {
            showMsg("请输入手机号");
            return;
        }

        showWaitDialog("短信发送中...");

        memberApi.send_mobile_code(password, new_phone, "savemobile", new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                CommFunMessage.hideWaitDialog();
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {
                        codeTimer();// 启动定时器
                    }
                    showMsg(msg);
                }
            }
        });
    }


    Timer timer;
    TimerTask task;

    @ViewInject(R.id.btn_send_code)
    private Button btn_send_code;

    @Event(value = R.id.btn_send_code)
    private void btn_get_codeClick(View view) {
        send_mobile_code();

    }

    final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            mHolder.setText(R.id.btn_send_code, "获取验证码(" + time_second_temp + ")");

            if (time_second_temp == 0) {
                time_second_temp = time_second;
                btn_send_code.setEnabled(true);
                mHolder.setText(R.id.btn_send_code, "获取验证码");
            }

            return false;
        }
    });

    int time_second = 60;

    private static int time_second_temp = 0;

    /**
     * 定时器
     */
    private void codeTimer() {
        try {
            if (time_second_temp == 0) {
                time_second_temp = time_second;
                if (timer != null)
                    timer.cancel();
                if (task != null)
                    task.cancel();
            }

            btn_send_code.setEnabled(false);

            timer = new Timer();

            long delay = 0;
            long intevalPeriod = 1 * 1000;
            // schedules the task to be run in an interval

            task = new TimerTask() {
                @Override
                public void run() {

                    time_second_temp--;

                    if (time_second_temp == 0) {
                        timer.cancel();
                    }

                    handler.sendEmptyMessage(0);
                }
            };

            timer.scheduleAtFixedRate(task, delay, intevalPeriod);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
