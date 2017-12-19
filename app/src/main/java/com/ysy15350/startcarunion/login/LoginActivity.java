package com.ysy15350.startcarunion.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.ysy15350.startcarunion.MainActivity;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.forgot_pwd.ForgotPwdActivity;
import com.ysy15350.startcarunion.register.RegisterActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Response;
import base.BaseData;
import base.config.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.ExitApplication;
import common.model.ScreenInfo;


@ContentView(R.layout.activity_login)
public class LoginActivity extends MVPBaseActivity<LoginViewInterface, LoginPresenter> implements LoginViewInterface {

    View contentView;

    @Override
    protected LoginPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new LoginPresenter(LoginActivity.this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        contentView = getWindow().getDecorView();

        String userName = CommFunAndroid.getSharedPreferences("userName");
        if (!CommFunAndroid.isNullOrEmpty(userName))
            mHolder.setText(R.id.et_userName, userName);

    }

    /**
     * 登录按钮点击事件
     *
     * @param view
     */
    @Event(value = R.id.btn_login)
    private void btn_loginClick(View view) {
        String userName = mHolder.getViewText(R.id.et_userName);
        String password = mHolder.getViewText(R.id.et_password);
        // String code = mHolder.getViewText(R.id.et_code);
        if (CommFunAndroid.isNullOrEmpty(userName)) {
            showMsg("请输入用户名或手机号");
            return;
        }

        if (CommFunAndroid.isNullOrEmpty(password)) {
            showMsg("请输入密码");
            return;
        }

        showWaitDialog("正在进行身份验证...");

        mPresenter.login(userName, password);

        // mPresenter.getData();//
        // 调用presenter的获取数据方法，在presenter类中调用bindData接口，本类实现了bindData方法
        //
    }

    @Override
    public void userLoginCallBack(Response response) {
        // TODO Auto-generated method stub
        hideWaitDialog();
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                String userName = mHolder.getViewText(R.id.et_userName);
                CommFunAndroid.setSharedPreferences("userName", userName);

                BaseData.getInstance().setUid(response.getUid());
                mPresenter.user_info();//获取用户信息

            }
            showMsg(msg);
        }
    }

    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    UserInfo userInfo = response.getData(UserInfo.class);
                    userInfo.setUid(BaseData.getInstance().getUid());
                    BaseData.getInstance().setUserInfo(userInfo);

                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();

                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 忘记密码
     *
     * @param view
     */
    @Event(value = R.id.tv_forgot_pwd)
    private void tv_forgot_pwdClick(View view) {

        String userName = mHolder.getViewText(R.id.et_userName);

        Intent intent = new Intent(this, ForgotPwdActivity.class);

        if (CommFunAndroid.isPhone(userName)) {
            intent.putExtra("phone", userName);
        }

        startActivity(intent);

    }

    /**
     * 注册
     *
     * @param view
     */
    @Event(value = R.id.tv_register)
    private void tv_registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);

        this.finish();
    }

    @Override
    protected void back() {
        super.back();
        ExitApplication.getInstance().exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ScreenInfo screenInfo = CommFunAndroid.getScreenInfo(this);
            showMsg(String.format("%f,%d,%d", screenInfo.getDensity(), screenInfo.getWidth(), screenInfo.getHeight()));
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            ExitApplication.getInstance().exit();
        }

        return super.onKeyDown(keyCode, event);
    }
}
