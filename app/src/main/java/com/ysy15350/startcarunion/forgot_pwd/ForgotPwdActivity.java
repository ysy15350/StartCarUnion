package com.ysy15350.startcarunion.forgot_pwd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.login.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import api.base.model.Response;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.model.ScreenInfo;


@ContentView(R.layout.activity_forgot_pwd)
public class ForgotPwdActivity extends MVPBaseActivity<ForgotPwdViewInterface, ForgotPwdPresenter>
        implements ForgotPwdViewInterface, Validator.ValidationListener {

    /**
     * 手机号
     */
    @ViewInject(R.id.et_mobile)
    @Pattern(regex = "^0?(13[0-9]|15[012356789]|17[0479]|18[01236789]|14[57])[0-9]{8}$", messageResId = R.string.register_phone_error)
    @Order(5)
    private EditText et_mobile;

    /**
     * 验证码
     */
    @ViewInject(R.id.et_verification_code)
    @NotEmpty(messageResId = R.string.register_hint_6)
    @Order(6)
    private EditText et_verification_code;


    /**
     * 密码
     */
    @ViewInject(R.id.et_password)
    @Password(messageResId = R.string.register_password_error)
//,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    @Order(7)
    private EditText et_password;

    /**
     * 重复密码
     */
    @ViewInject(R.id.et_password1)
    @ConfirmPassword(messageResId = R.string.register_password_different)
    @Order(8)
    private EditText et_password1;

    /**
     * 表单验证器
     */
    Validator validator;


    @Override
    protected ForgotPwdPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new ForgotPwdPresenter(ForgotPwdActivity.this);
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 0)
            setFormHead("忘记密码");
        else {
            setFormHead("修改密码");
            mHolder.setVisibility_GONE(R.id.ll_top);
        }

        if (intent != null) {
            String phone = intent.getStringExtra("phone");
            mHolder.setText(R.id.et_mobile, phone);
        }

        validator = new Validator(this);

        validator.setValidationListener(this);
    }


    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        validator.validate();

        if (validationSucceeded) {

            String password = mHolder.getViewText(R.id.et_password1);
            String mobile = mHolder.getViewText(R.id.et_mobile);
            String mobile_code = et_verification_code.getText().toString().trim();


            mPresenter.save_password(password, mobile, mobile_code);
        }
    }

    @Override
    public void save_passwordCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Timer timer;
    TimerTask task;

    @ViewInject(R.id.btn_send_code)
    private Button btn_send_code;

    @Event(value = R.id.btn_send_code)
    private void btn_get_codeClick(View view) {


        String phone = mHolder.getViewText(R.id.et_mobile);

        if (CommFunAndroid.isNullOrEmpty(phone)) {
            showMsg("请输入手机号");
            return;
        }

        mPresenter.send_mobile_code(phone);

    }

    @Override
    public void send_mobile_codeCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    codeTimer();// 启动定时器
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            mHolder.setText(R.id.btn_send_code, "获取验证码(" + time_second_temp + ")");

            if (time_second_temp == 0) {
                time_second_temp = time_second;
                btn_send_code.setEnabled(true);
                btn_send_code.setTextColor(Color.parseColor("#1ed5c1"));
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
            btn_send_code.setTextColor(Color.GRAY);


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


    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }

    @Override
    protected void back() {
        super.back();
        //startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ScreenInfo screenInfo = CommFunAndroid.getScreenInfo(this);
            showMsg(String.format("%f,%d,%d", screenInfo.getDensity(), screenInfo.getWidth(), screenInfo.getHeight()));
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
