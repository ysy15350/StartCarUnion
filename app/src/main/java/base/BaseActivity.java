package base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.StartActivity;
import com.ysy15350.startcarunion.login.LoginActivity;
import com.ysy15350.startcarunion.mine.my_info.MyInfoActivity;
import com.ysy15350.startcarunion.mine.my_shop.EditShopActivity;
import com.ysy15350.startcarunion.register.RegisterActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import base.adapter.ViewHolder;
import common.AppStatusManager;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.RequestPermissionType;


/**
 * Created by yangshiyou on 2016/11/29.
 */

public class BaseActivity extends AppCompatActivity implements IView {

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    /**
     * 控件ViewGroup
     */
    protected View mContentView;

    protected ViewHolder mHolder;

    /**
     * 界面标题
     */
    protected String mTitle = "";

    /**
     * 是否需要登录
     */
    boolean mNeedLogin = false;


    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ACTIVITY_COUNT++;
        String str = this.toString();
        activityNames.add(str);

        StringBuilder sb = new StringBuilder();

        for (String activityName :
                activityNames) {
            sb.append(activityName + "\n");
        }
        Log.d(TAG, "onCreate() called with: ******************activityNames****************** = [" + sb.toString() + "]");

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]" + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);

        if (this instanceof MyInfoActivity || this instanceof EditShopActivity || this instanceof RegisterActivity) {
            //showMsg("不检查");
        } else {
            checkAppStatus();//如果是重新打开的应用，重新从入口进入，缺陷：不会回到选择照片的页面
        }


        x.view().inject(this);

        //ExitApplication.getInstance().addActivity(this);// 添加当前Activity


        mContentView = getWindow().getDecorView();

        mHolder = ViewHolder.get(this, mContentView);

        init();
    }

    //http://blog.csdn.net/u011511577/article/details/54603256
    protected void checkAppStatus() {
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            //该应用已被回收，应用启动入口SplashActivity，走重启流程
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //showMsg("应用被回收重新执行");
        }
    }


    /**
     * 初始化，1：initView；2：readCahce；3：loadData；4：bindData
     */
    private void init() {

        initView();

        initData();

        readCahce();

        loadData();

        bindData();
    }

    /**
     * 初始化，1：initView；2：readCahce；3：loadData；4：bindData
     *
     * @param context
     * @param contentView
     * @param title
     * @param isNeedLogin
     */
    public void init(Context context, View contentView, String title, boolean isNeedLogin) {

        mContentView = contentView;
        mTitle = title;
        mNeedLogin = isNeedLogin;


        initView();

        initData();

        readCahce();

        loadData();

        bindData();
    }

    /**
     * 设置头部(带返回箭头)
     *
     * @param title
     */
    protected void setFormHead(String title) {
        setTitle(title);
        setBtnBack(true);
    }

    /**
     * 设置菜单
     *
     * @param menu
     */
    protected void setMenuText(String menu) {
        if (!CommFunAndroid.isNullOrEmpty(menu)) {
            mHolder.setVisibility_VISIBLE(R.id.tv_menu).setText(R.id.tv_menu, menu);
        }
    }

    /**
     * 设置标题（不带返回箭头）
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (!CommFunAndroid.isNullOrEmpty(title))
            mHolder.setText(R.id.tv_form_title, title);
    }

    /**
     * 设置是否显示返回箭头
     *
     * @param isBack
     */
    protected void setBtnBack(boolean isBack) {
        if (isBack)
            mHolder.setVisibility_VISIBLE(R.id.btn_back);
        else
            mHolder.setVisibility_GONE(R.id.btn_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        // 填充状态栏
        CommFunAndroid.fullScreenStatuBar(this);
    }

    @Override
    public void readCahce() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void bindData() {

    }

    @Override
    public void showMsg(String msg) {
        if (CommFunAndroid.isNullOrEmpty(msg))
            return;
        CommFunMessage.showMsgBox(msg);
    }

    @Override
    public void showWaitDialog(String msg) {
        if (CommFunAndroid.isNullOrEmpty(msg))
            return;
        CommFunMessage.showWaitDialog(this, msg);
    }

    @Override
    public void hideWaitDialog() {
        CommFunMessage.hideWaitDialog();
    }

    @Override
    public void setViewText(int id, CharSequence text) {
        if (mHolder != null)
            mHolder.setText(id, text);
    }

    @Override
    public String getViewText(int id) {
        if (mHolder != null)
            return mHolder.getViewText(id);
        return "";
    }

    @Override
    public void setTextColor(int id, int color) {
        if (mHolder != null)
            mHolder.setTextColor(id, color);
    }

    @Override
    public void setBackgroundColor(int id, int color) {
        if (mHolder != null)
            mHolder.setBackgroundColor(id, color);
    }

    @Override
    public void setVisibility_GONE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_GONE(id);
    }

    @Override
    public void setVisibility_VISIBLE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_VISIBLE(id);

    }

    /**
     * 返回
     *
     * @param view
     */
    @Event(value = R.id.btn_back)
    private void btn_backClick(View view) {
        back();
        finish();

    }

    protected void back() {

    }

    protected boolean isLogin() {
        if (BaseData.getInstance().getUid() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);

            return false;
        }

        return true;
    }


    //重写会导致Android4.4报错
    //java.lang.ClassNotFoundException: Didn’t find class “android.os.PersistableBundle” on path: DexPathList
    //    @Override
    //    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    //        super.onSaveInstanceState(outState, outPersistentState);
    //        Log.i(TAG, "<<<<<<<<<<<<<<<**********onSaveInstanceState*************>>>>>>>>>>>>>>" + this.toString());
    //    }

    //    在手机上调用系统相机的时候，有很大的几率会导致内存不足从而调用相机的app或者app的调用相机的Activity界面被强制回收
    // ，所以调用相机的Activity重写 onSaveInstanceState 是非常必要的，
    // 在onSaveInstanceState方法中将局部变量保存起来，
    // 同时在  onRestoreInstanceState  方法中重新获取这些局部变量并做必要的逻辑处理。

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "<<<<<<<<<<<<<<<**********onRestoreInstanceState*************>>>>>>>>>>>>>>" + this.toString());
    }

    //    @Override
    //    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
    //        super.onRestoreInstanceState(savedInstanceState, persistentState);
    //        Log.i(TAG, "<<<<<<<<<<<<<<<**********onRestoreInstanceState*************>>>>>>>>>>>>>>" + this.toString());
    //    }

    //    @Override
    //    public void onConfigurationChanged(Configuration newConfig) {
    //        super.onConfigurationChanged(newConfig);
    //        Log.i(TAG, "<<<<<<<<<<<<<<<**********onConfigurationChanged*************>>>>>>>>>>>>>>" + this.toString());
    //    }

    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.btn_ok), null, getString(R.string.btn_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    private AlertDialog mAlertDialog;

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }


    /**
     * 申请权限
     */
    public boolean callPhoneCheckPermission(Activity context, String phone) {

        Log.d(TAG, "callPhoneCheckPermission() called with: context = [" + context + "], phone = [" + phone + "]");

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);

                CommFunAndroid.setSharedPreferences("phone", phone);
                return false;
            } else {
                CommFunAndroid.callPhone(context, phone);
            }
        } else {
            CommFunAndroid.callPhone(context, phone);
        }

        return true;
    }

    /**
     * 申请读取文件权限
     */
    public boolean callReadExtrnalStoreagePermission(Activity activity) {
        Log.d(TAG, "callReadExtrnalStoreagePermission() called with: activity = [" + activity + "]");

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            Log.d(TAG, "callReadExtrnalStoreagePermission() called with: checkCallPhonePermission = [" + checkCallPhonePermission + "]");

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        activity
                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE);
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult() called with: requestCode = [" + requestCode + "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // showMsg("允许");
                    CommFunAndroid.callPhone(this, CommFunAndroid.getSharedPreferences("phone"));
                } else {
                    //showMsg("拒绝");
                    // Permission Denied
                    Toast.makeText(this, "CALL_PHONE Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMsg("你允许读取文件");

                } else {
                    showMsg("你已拒绝读取文件请求");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommFunMessage.hideWaitDialog();
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        getWindow().setBackgroundDrawable(null);// android的默认背景是不是为空。
        super.onDestroy();
        Log.d(TAG, "onDestroy() called" + this + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);
        System.gc();

        //mHolder.destoryImageView();
    }

    public static int ACTIVITY_COUNT = 0;
    public static List<String> activityNames = new ArrayList<>();

    @Override
    protected void finalize() throws Throwable {
        ACTIVITY_COUNT--;
        String str = this.toString();
        if (activityNames.contains(str)) {
            activityNames.remove(str);
        }
        Log.d(TAG, "finalize() called" + this + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);
        super.finalize();
    }
}