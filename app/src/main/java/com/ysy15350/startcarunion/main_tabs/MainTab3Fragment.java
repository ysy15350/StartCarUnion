package com.ysy15350.startcarunion.main_tabs;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.login.LoginActivity;
import com.ysy15350.startcarunion.mine.SettingActivity;
import com.ysy15350.startcarunion.mine.my_collection.MyCollectionActivity;
import com.ysy15350.startcarunion.mine.my_comment.MyCommentActivity;
import com.ysy15350.startcarunion.mine.my_contacts.MyContactsActivity;
import com.ysy15350.startcarunion.mine.my_footprint.MyFootprintActivity;
import com.ysy15350.startcarunion.mine.my_info.MyInfoActivity;
import com.ysy15350.startcarunion.mine.my_msg.MyMsgActivity;
import com.ysy15350.startcarunion.mine.my_score.MyScoreActivity;
import com.ysy15350.startcarunion.mine.my_shop.EditShopActivity;
import com.ysy15350.startcarunion.mine.my_visitor.MyVisitorActivity;
import com.ysy15350.startcarunion.register.RegisterActivity;
import com.ysy15350.startcarunion.transaction.ContactsUsActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.PublicApi;
import api.base.model.Config;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.PublicApiImpl;
import api.model.Version;
import base.BaseData;
import base.config.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFunAndroid;
import custom_view.popupwindow.CallPopupWindow;
import custom_view.qrcode.CanvasRQ;

import static common.CommFunAndroid.callPhoneCheckPermission;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab3)
public class MainTab3Fragment extends MVPBaseFragment<MainTab3ViewInterface, MainTab3Presenter>
        implements MainTab3ViewInterface {

    public MainTab3Fragment() {
    }

    @Override
    public MainTab3Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab3Presenter(getActivity());
    }

    @Override
    public void onResume() {
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


            CallPopupWindow popupWindow = new CallPopupWindow(getActivity(), phones);

            popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                @Override
                public void onCallMobile(String mobile) {
                    //showMsg(mobile);
                    callPhoneCheckPermission(getActivity(), mobile);
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


}
