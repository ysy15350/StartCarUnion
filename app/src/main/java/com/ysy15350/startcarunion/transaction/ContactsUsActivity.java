package com.ysy15350.startcarunion.transaction;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.PublicApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.PublicApiImpl;
import api.model.Version;
import base.BaseActivity;
import common.CommFunAndroid;
import custom_view.popupwindow.CallPopupWindow;
import custom_view.qrcode.CanvasRQ;

/**
 * Created by yangshiyou on 2017/9/8.
 */

@ContentView(R.layout.activity_contact_us)
public class ContactsUsActivity extends BaseActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setBackgroundDrawable(null);
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public void initView() {
        super.initView();
        setFormHead("联系我们");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkVersion();
    }


    @Event(value = R.id.ll_msg)
    private void ll_msgClick(View view) {
        String phone = mHolder.getViewText(R.id.tv_phone);
        if (!CommFunAndroid.isNullOrEmpty(phone)) {

            String toNumbers = "";


            if (phone.contains("/")) {
                String[] arr = phone.split("/");
                if (arr != null && arr.length > 0) {
                    for (String str :
                            arr) {
                        toNumbers = toNumbers + str + ";";
                    }
                    toNumbers = toNumbers.substring(0, toNumbers.length() - 1);
                }
            } else
                toNumbers = phone;


            Uri sendSmsTo = Uri.parse("smsto:" + toNumbers);
            Intent intent = new Intent(android.content.Intent.ACTION_SENDTO, sendSmsTo);
            intent.putExtra("sms_body", "你好，");
            startActivity(intent);

        }
    }


    /**
     * 拨打电话
     *
     * @param view
     */
    @Event(value = R.id.ll_phone)
    private void ll_phoneClick(View view) {
        String phone = mHolder.getViewText(R.id.tv_phone);
        if (!CommFunAndroid.isNullOrEmpty(phone)) {
            List<String> phones = new ArrayList<>();

            if (phone.contains("/")) {
                String[] arr = phone.split("/");
                if (arr != null && arr.length > 0) {
                    for (String str :
                            arr) {
                        phones.add(str);
                    }
                }
            } else
                phones.add(phone);


            CallPopupWindow popupWindow = new CallPopupWindow(this, phones);

            popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                @Override
                public void onCallMobile(String mobile) {
                    //showMsg(mobile);
                    callPhoneCheckPermission(ContactsUsActivity.this, mobile);
                }


                @Override
                public void onCancelClick() {

                }
            });
            popupWindow.showPopupWindow(mContentView);
        }
    }

    @ViewInject(R.id.img_qr_code)
    private CanvasRQ img_qr_code;

    private void checkVersion() {

        try {
            //保证有更新信息，目的获取下载地址，显示二维码
            int version_code = 0;//CommFunAndroid.getAppVersionCode(mContext);
            PublicApi publicApi = new PublicApiImpl();
            publicApi.checkVersion(version_code, new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {
                    super.onSuccess(isCache, response);


                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            Version version = response.getData(Version.class);
                            if (version != null) {
                                String url = version.getFile_url();
                                //http://qidian.59156.cn/Uploads/Download/2017-09-13/59b909abec192.apk
                                //showMsg(url);
                                img_qr_code.setUrl(url);
                            }
                        }
//                        else {
//                            showMsg(msg);
//                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
