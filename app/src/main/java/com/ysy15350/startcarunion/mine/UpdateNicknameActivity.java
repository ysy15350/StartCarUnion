package com.ysy15350.startcarunion.mine;

import android.view.View;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.MemberApi;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.BaseActivity;

/**
 * 修改昵称
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_update_nickname)
public class UpdateNicknameActivity extends BaseActivity {

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("修改昵称");
    }

    /**
     * 修改昵称
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        String nickName = mHolder.getViewText(R.id.et_nickname);

        updateNickName(nickName);

    }

    private void updateNickName(String name) {
        MemberApi memberApi = new MemberApiImpl();
        memberApi.save_info("nickname", name, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);


                try {
                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            finish();
                        }
                        showMsg(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
