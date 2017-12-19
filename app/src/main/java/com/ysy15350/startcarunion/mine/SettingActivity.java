package com.ysy15350.startcarunion.mine;


import android.content.Intent;
import android.view.View;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.dialog.AddContactNumberDialog;
import com.ysy15350.startcarunion.dialog.ClearCacheDialog;
import com.ysy15350.startcarunion.dialog.UpdateVersionDialog;
import com.ysy15350.startcarunion.log.MainActivity;
import com.ysy15350.startcarunion.login.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;

import api.PublicApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.PublicApiImpl;
import api.model.Version;
import base.BaseActivity;
import base.BaseData;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.cache.ACache;

/**
 * 设置
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    private int count;

    @Override
    public void initView() {

        super.initView();
        setFormHead("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String vesionName = CommFunAndroid.getAppVersionName(getApplicationContext());
        mHolder.setText(R.id.tv_version, "当前版本号：" + vesionName);
    }

    /**
     * 清除缓存
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        ClearCacheDialog dialog = new ClearCacheDialog(this);
        dialog.setDialogListener(new ClearCacheDialog.DialogListener() {

            @Override
            public void onOkClick() {
                File file = new File(CommFunAndroid.getDiskCachePath() + "/aandroid_log/error");
                clearErrorLog(file);

                if (ACache.aCache != null)
                    ACache.aCache.clear();

                showMsg("已清除");
            }

            @Override
            public void onCancelClick() {

            }
        });
        dialog.show();
    }

    private void clearErrorLog(File file) {
        try {

            if (!file.exists())
                return;
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                clearErrorLog(files[i]);
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 检测更新
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        showWaitDialog("版本检测中...");
        checkVersion();

//        ConfirmDialog dialog = new ConfirmDialog(this);
//        dialog.setDialogListener(new ConfirmDialog.DialogListener() {
//
//            @Override
//            public void onOkClick() {
//
//            }
//
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        dialog.show();

    }

    /**
     * 联系我们
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {

//        LoadingDialog dialog = new LoadingDialog(this);
//        dialog.show();

    }

    /**
     * 注销
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        BaseData.getInstance().setUid(0);
        Intent intent = getIntent();
        setResult(1, intent);//让主页关闭

        startActivity(new Intent(this, LoginActivity.class));
        //this.finish();
        //new MessageToast(this, R.mipmap.icon_success, "操作成功").show();
    }


    @Event(value = R.id.ll_system)
    private void ll_systemClick(View view) {
        count++;
        if (count == 5) {
            count = 0;
            AddContactNumberDialog dialog = new AddContactNumberDialog(this);
            dialog.setDialogListener(new AddContactNumberDialog.DialogListener() {
                @Override
                public void onCancelClick() {

                }

                @Override
                public void onOkClick(String tell, int type) {
                    if (!CommFunAndroid.isNullOrEmpty(tell)) {
                        if (CommFunAndroid.isEquals(tell, "15215095191")) {

                            Intent intent = new Intent(SettingActivity.this, MainActivity.class);

                            startActivity(intent);
                        }
                    }
                }
            });
            dialog.show();
        }
    }


    private void checkVersion() {

        try {
            int version_code = CommFunAndroid.getAppVersionCode(getApplicationContext());
            PublicApi publicApi = new PublicApiImpl();
            publicApi.checkVersion(version_code, new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {
                    super.onSuccess(isCache, response);

                    CommFunMessage.hideWaitDialog();

                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            checkUpdate(response);
                        } else {
                            showMsg(msg);
                        }
                    }
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
                        String versionName = version.getVersionName();

//                        DownloadDialog dialog = new DownloadDialog(this, "版本更新(" + version.getVersionName() + ")",
//                                version.getVersionName(), version.getDescription(), version.getFileSize(),
//                                version.getFile_url());
                        // ClearCacheDialog dialog = new ClearCacheDialog(this);

                        UpdateVersionDialog dialog = new UpdateVersionDialog(this, "版本更新(" + version.getVersionName() + ")",
                                version.getVersionName(), version.getDescription(), version.getFileSize(),
                                version.getFile_url());
                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
