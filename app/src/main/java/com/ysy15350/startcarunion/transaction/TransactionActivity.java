package com.ysy15350.startcarunion.transaction;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;

import java.util.Map;

import api.IndexApi;
import api.base.model.Config;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.IndexApiImpl;
import base.BaseActivity;
import base.BaseData;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/8.
 */

@ContentView(R.layout.activity_transaction)
public class TransactionActivity extends BaseActivity {

    @Override
    public void initView() {
        super.initView();
        setFormHead("担保交易");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String icon = BaseData.getCache("guarantee_code");
        if (!CommFunAndroid.isNullOrEmpty(icon))
            mHolder.setImageURL(R.id.img_qr_code, Config.getServer() + icon);
        guarantee_code();
    }


    public void guarantee_code() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.guarantee_code(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {
                        Map<String, String> map = response.getData(new TypeToken<Map<String, String>>() {
                        }.getType());
                        if (map != null && map.containsKey("icon")) {
                            String icon = map.get("icon");
                            if (!CommFunAndroid.isNullOrEmpty(icon)) {
                                BaseData.setCache("guarantee_code", icon);
                                mHolder.setImageURL(R.id.img_qr_code, Config.getServer() + icon);
                            }
                        }
                    }

                }
            }
        });
    }
}
