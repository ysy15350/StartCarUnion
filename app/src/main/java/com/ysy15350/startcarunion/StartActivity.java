package com.ysy15350.startcarunion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.ysy15350.startcarunion.dialog.UpdateVersionDialog;
import com.ysy15350.startcarunion.login.LoginActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import api.PublicApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.PublicApiImpl;
import api.model.Version;
import base.BaseActivity;
import base.BaseData;
import base.config.BaseInfoDao;
import base.config.entity.BaseInfo;
import common.AppStatusManager;
import common.CommFun;
import common.CommFunAndroid;
import common.ExitApplication;
import common.JsonConvertor;
import custom_view.dialog.ConfirmDialog;

import static java.lang.System.currentTimeMillis;


/**
 * Created by yangshiyou on 2017/9/1.
 */

@ContentView(R.layout.activity_start)
public class StartActivity extends BaseActivity {

    // 快捷键 logt
    private static final String TAG = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusManager.AppStatusConstant.APP_NORMAL);
        super.onCreate(savedInstanceState);


        //快捷键  logd
        Log.d(TAG, "onCreate: ");

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        CommFunAndroid.fullScreenStatuBar(this);

//        String deviceId = CommFunAndroid.getDeviceId(getApplicationContext());
//        CommFunAndroid.setSharedPreferences("device_id", deviceId);


        //App打包成apk后在部分手机上安装，安装完成后直接点击系统安装界面上的打开按钮后点击home键到home页，
        //再点击启动图标，应用重复启动（该问题只出现在第一次安装app的时候，之后退出app再重新运行，点击home，再点击启动图标不会出现该问题）

        //解决方案，在启动页增加代码判断，如果启动的Activity不是当前任务的根Activity则直接finish掉，代码如下:

        //判断根Activity代码
        if (!isTaskRoot()) {
            Log.d(TAG, "onCreate() called with: isTaskRoot = [" + isTaskRoot() + "]");
            finish();
            return;
        }
        registerMessageReceiver();//注册广播接收器
    }


    @Override
    protected void onResume() {
        super.onResume();


//        UserApi userApi = new UserApiImpl();
//        userApi.userLogin("admin", "12346", new ApiCallBack() {
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                super.onSuccess(isCache, response);
//            }
//        });//测试

        //-------错误测试，在MyApplication注册的CrashHandler类是否能记录日志-------------

//        String s = null;
//        if (s.equals("")) {
//
//        }

//        try {
//            int a = 3 / 0;
//        } catch (Exception ex) {
//            FileUtils.writeErrorLog(ex);
//        }

        //----------------end-------------------------------------


        String img_url = BaseData.getCache("img_url");
        if (!CommFun.isNullOrEmpty(img_url)) {
            mHolder.setImageURL(R.id.img_ad, img_url, 720, 1005);
        }


        int type = CommFunAndroid.getConnectedType(this);

        checkNetWork(type);//检查网络

        Log.d(TAG, "onResume() called");


    }

    int network_type = -1;

    private void checkNetWork(int type) {

        Log.d(TAG, "checkNetWork() called with: type = [" + type + "]");

        if (seconds > 0)//说明已经执行网络请求
        {
            if (type == network_type)//如果网络没有发生变化,避免网络变化通知发起的检查，多次调用
                return;
        }

        try {

            network_type = CommFunAndroid.getConnectedType(this);

            if (network_type == -1) {

                stopTimer();

                mHolder.setVisibility_VISIBLE(R.id.tv_retry);

                Bundle bundle = new Bundle();
                bundle.putString("content", "无网络/(ㄒoㄒ)/~~");
                Message msg = new Message();
                msg.what = 1;
                msg.setData(bundle);
                mHandler.sendMessage(msg);

                showConfirm();//提示确认消息

                return;
            } else if (0 == network_type) {
//            0:mobile(手机网路);1:WIFI;
                showMsg("正在使用手机网络");

            } else if (1 == network_type) {
                showMsg("正在使用WIFI网络");
            }

            startTimer();

            uploadErrorLog();//上传错误日志
            mHolder.setVisibility_GONE(R.id.tv_retry);

            if (checkToken() == 0) {
                getToken();//启动时更新token,不管是否token过期
            } else {
                checkVersion();
            }
        } catch (Exception e) {
            stopTimer();
            e.printStackTrace();
        }
    }

    /**
     * 是否需要重新获取token,true:需要
     *
     * @return
     */
    private int checkToken() {

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
        Log.d(TAG, "checkToken() called differTime=" + differTime);
        if (tokenTime == 0 || CommFunAndroid.isNullOrEmpty(token) || differTime > 600 * 1000) {//10分钟内重启不获取token,不检查更新，直接跳转
            tokenStatus = 0;//0:无效；
            return 0;
        }
        tokenStatus = 1;//1:有效
        return 1;
    }

    /**
     * 提示框，设置网络
     */
    private void showConfirm() {
        //new MessageToast(mContext, R.mipmap.icon_no_network, "手机无网络").show();
        ConfirmDialog confirmDialog = new ConfirmDialog(this, "网络连接错误", "去设置网络?", "设置", "退出", R.mipmap.icon_no_network);
        confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
            @Override
            public void onCancelClick() {
                StartActivity.this.finish();
                ExitApplication.getInstance().exit();
            }

            @Override
            public void onOkClick() {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
        confirmDialog.show();
    }

    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    int seconds = 0;

    private void startTimer() {

        try {
            if (timer != null)
                timer.cancel();

            timer = new Timer();

            if (task != null)
                task.cancel();

            seconds = 0;

            task = new TimerTask() {

                @Override
                public void run() {
                    seconds++;
                    mHandler.sendEmptyMessage(0);
                }
            };

            timer.schedule(task, 0, 1000);
        } catch (Exception e) {
            mHolder.setText(R.id.tv_content, "系统错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void stopTimer() {
        Log.d(TAG, "stopTimer() called");
        try {
            if (timer != null)
                timer.cancel();
            if (task != null) {
                task.cancel();
            }
            seconds = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广告时间
     */
    private final int ad_time = 4;

    Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, msg.what + "handleMessage: " + seconds);

            String content = String.format("正在加载...(%d)", seconds);

            int time = ad_time - seconds;

            if (time >= 0)
                mHolder.setText(R.id.tv_time, time + "");

            switch (msg.what) {
                case 0:
                    if (seconds >= ad_time) {//3秒
                        checkStatus();
                    }
                    break;
                case 1://无网络
                    stopTimer();

                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String str = bundle.getString("content");
                        if (!CommFunAndroid.isNullOrEmpty(str)) {
                            content = str;
                        }
                    }

                    break;
                default:
                    break;
            }

            mHolder.setText(R.id.tv_content, content);
        }
    };

    private void checkStatus() {
//        checkVersionCode  检测版本接口返回状态
//        tokenStatus  0：无效；1：有效

        if (tokenStatus == 0) {
            getToken();
        }
        if (checkVersionCode == 0) {
            checkVersion();
        }

        if (checkVersionCode != 0 && checkVersionCode != 200 && tokenStatus == 1) {
            checkLogin();
            stopTimer();
        }

    }

    PublicApi publicApi = new PublicApiImpl();

    /**
     * token状态;0：无效；1：有效
     */
    int tokenStatus = 0;

    private void getToken() {

//        CommFunMessage.showWaitDialog("正在初始化...");


        String className = this.getClass().getName();
        publicApi.getToken(className, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {


                if (response != null) {

                    //快捷键  logm
                    Log.d(TAG, "getToken onSuccess() called with: isCache = [" + isCache + "], response.result = [" + response.getResultJson() + "]");


                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {
                        tokenStatus = 1;
                        String token = response.getToken();
                        if (!CommFunAndroid.isNullOrEmpty(token)) {

                            BaseData.setToken(token);
                            Log.i("token", token);

                            //checkLogin();
                            checkVersion();


                        }
                    } else {
                        tokenStatus = 0;
                        mHolder.setText(R.id.tv_content, msg);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                stopTimer();

                int type = CommFunAndroid.getConnectedType(getApplicationContext());
                if (type != network_type)//如果网络发生变化会触发onFailed,如果和之前的网络不同，重新检查网络
                {
                    checkNetWork(type);//检查网络
                } else {
                    mHolder.setVisibility_VISIBLE(R.id.tv_retry);
                    mHolder.setText(R.id.tv_content, "服务器连接失败,请联系管理员：\n" + msg);
                    ConfirmDialog confirmDialog = new ConfirmDialog(StartActivity.this, "服务器连接失败", msg + "\n请联系管理员", "查看网络", "退出");
                    confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {
                            StartActivity.this.finish();
                            ExitApplication.getInstance().exit();
                        }

                        @Override
                        public void onOkClick() {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    confirmDialog.show();
                }
            }
        });
    }




    /**
     * 重试
     *
     * @param view
     */
    @Event(value = R.id.tv_retry)
    private void tv_retryClick(View view) {
        int type = CommFunAndroid.getConnectedType(this);

        checkNetWork(type);//检查网络
    }


    int checkVersionCode = 0;

    private void checkVersion() {

        try {

            int version_code = CommFunAndroid.getAppVersionCode(getApplicationContext());
            publicApi.checkVersion(version_code, new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {
                    super.onSuccess(isCache, response);


                    if (response != null) {
                        Log.d(TAG, "checkVersion onSuccess() called with: isCache = [" + isCache + "], response.result = [" + response.getResultJson() + "]");
                        int code = response.getCode();
                        String msg = response.getMessage();
                        checkVersionCode = code;
                        if (code == 200) {
                            checkUpdate(response);
                        }
                    }
                }

                @Override
                public void onFailed(String msg) {
                    super.onFailed(msg);
                    checkLogin();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkUpdate(Response response) {
        try {
            if (response != null) {
                if (response.getCode() == 200) {
                    Version version = response.getData(Version.class);
                    if (version != null) {
                        UpdateVersionDialog dialog = new UpdateVersionDialog(this, "版本更新(" + version.getVersionName() + ")",
                                version.getVersionName(), version.getDescription(), version.getFileSize(),
                                version.getFile_url());

                        dialog.setDialogListener(new UpdateVersionDialog.DialogListener() {
                            @Override
                            public void onCancelClick() {
                                showWaitDialog("正在验证身份...");

                                checkLogin();
                            }

                            @Override
                            public void onOkClick() {

                            }
                        });

                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static boolean isStartMainActivity = false;


    private void checkLogin() {
        Log.d(TAG, "checkLogin() called");
        String token = "";
        int uid = 0;
        try {
            token = BaseData.getInstance().getToken();
            uid = BaseData.getInstance().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CommFunAndroid.isNullOrEmpty(token)) {
            mHolder.setText(R.id.tv_content, "身份验证失败");
            return;
        }
        if (uid == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            if (!isStartMainActivity) {
                isStartMainActivity = true;
                Log.d(TAG, "checkLogin: startActivity MainActivity");
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            }
        }
    }


    public static final String MESSAGE_RECEIVED_ACTION = "com.ysy15350.startcarunion.MESSAGE_RECEIVED_ACTION";

    /**
     * 广播接收
     */
    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {

        try {
            mMessageReceiver = new MessageReceiver();
            IntentFilter filter = new IntentFilter();
            filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
            filter.addAction(MESSAGE_RECEIVED_ACTION);
            registerReceiver(mMessageReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {


                int type = intent.getIntExtra("type", -1);
                if (type != -1) {
                    checkNetWork(type);//检查网络
                }
//                String msg = intent.getStringExtra("msg");
//                if (type != -1) {
//                    getToken();
//                }
//
//                showMsg(msg);

            }
        }
    }

    private void checkPermission() {
        Log.d(TAG, "callPhoneCheckPermission:value= " + callPhoneCheckPermission(this, ""));//拨打电话权限
        Log.d(TAG, "callReadExtrnalStoreagePermission:value= " + callReadExtrnalStoreagePermission(this));//读取文件权限
    }


    private void uploadErrorLog() {
        checkPermission();


        if (!callReadExtrnalStoreagePermission(this))
            return;

        String errorLogContent = CommFunAndroid.getErrorLogContent();


        Log.d(TAG, "uploadErrorLog() called");

        if (CommFunAndroid.isNullOrEmpty(errorLogContent))
            return;

        String deviceInfo = JsonConvertor.toJson(CommFunAndroid.getDeviceInfo());

        RequestParams requestParams = new RequestParams("http://www.ysy15350.com/api/app/errorLog");
        requestParams.addBodyParameter("appName", "easyquick" + CommFunAndroid.getAppVersionName(getApplicationContext()));
        requestParams.addBodyParameter("content", deviceInfo + "\nerror" + errorLogContent);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                CommFunAndroid.clearLog();
                Log.i(TAG, "onSuccess: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        try {
            stopTimer();
            if (mMessageReceiver != null)
                unregisterReceiver(mMessageReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
