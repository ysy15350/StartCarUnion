package com.ysy15350.startcarunion.log;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.databinding.ActivityLogDetailBinding;
import com.ysy15350.startcarunion.log.model.RequestLog;

import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;
import base.BaseActivity;
import common.CommFunAndroid;
import common.JsonConvertor;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class LogDetailActivity extends BaseActivity {

    ActivityLogDetailBinding binding;

    RequestLog requestLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        setFormHead("接口访问详情");
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        requestLog = (RequestLog) intent.getSerializableExtra("log");
    }

    Map<String, String> mParams;

    @Override
    public void bindData() {
        super.bindData();
        try {
            if (requestLog != null) {

                String paramsStr = requestLog.getParamStr();

                List<Map.Entry<String, String>> mappingList = getParamsList(paramsStr);

                createParamsEditText(mappingList);

                binding.setLog(requestLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {
        RequestLog log = binding.getLog();

        request();
    }

    /**
     * 获取参数集合
     *
     * @param paramsStr
     * @return
     */
    private List<Map.Entry<String, String>> getParamsList(String paramsStr) {
        try {
            if (!CommFunAndroid.isNullOrEmpty(paramsStr)) {

                mParams = JsonConvertor.fromJson(paramsStr, new TypeToken<Map<String, String>>() {
                }.getType());

                if (mParams != null) {
                    List<Map.Entry<String, String>> mappingList = new ArrayList<Map.Entry<String, String>>(mParams.entrySet());


                    return mappingList;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Map.Entry<String, String>> getParamsList() {
        try {

            if (mParams != null) {
                List<Map.Entry<String, String>> mappingList = new ArrayList<Map.Entry<String, String>>(mParams.entrySet());


                return mappingList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    LinearLayout ll_params;

    private void createParamsEditText(List<Map.Entry<String, String>> mappingList) {

        try {
            if (mappingList != null && !mappingList.isEmpty()) {

                showMsg(mappingList.size() + "mappingList.size()");

                ll_params = mHolder.getView(R.id.ll_params);

                for (Map.Entry<String, String> entry : mappingList) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    EditText editText = createEditText(key, value);
                    ll_params.addView(editText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EditText createEditText(String key, String value) {
        EditText editText = new EditText(this);

        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        editText.setLayoutParams(layoutParams);

        editText.setBackground(null);

        editText.setTextColor(Color.parseColor("#000000"));

        //editText.setTag(1, key);
        editText.setTag(key);

        editText.setText(value);

        return editText;

    }

    private void getUpdatedParams() {

        if (ll_params != null) {
            int count = ll_params.getChildCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    View view = ll_params.getChildAt(i);

                    String tag = view.getTag() == null ? null : view.getTag().toString();

                    if (!CommFunAndroid.isNullOrEmpty(tag)) {


                        View editText = ll_params.findViewWithTag(tag);
                        if (editText instanceof EditText) {
                            String value = ((EditText) editText).getText().toString().trim();

                            showMsg(tag + "," + value);
                            mParams.put(tag, value);
                        }
                    }
                }
            }
        }
    }

    private void request() {
        if (requestLog != null) {
            IServer server = new Request();
            server.setUrl(requestLog.getUrl());

            List<Map.Entry<String, String>> paramsList = getParamsList();
            if (paramsList != null && !paramsList.isEmpty()) {
                for (Map.Entry<String, String> entry : paramsList) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    server.setParam(key, value);

                    //showMsg(key + "," + value);
                }
            }

            server.setApiCallBack(new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {

                }

                @Override
                public void onSuccess(boolean isCache, String data) {
                    //showMsg(data);
                    requestLog.setResponseStr("new:" + data);
                    binding.setLog(requestLog);
                }
            });

            showMsg(requestLog.getCacheTime().intValue() + "");

            server.setCacheTime(requestLog.getCacheTime().intValue());

            server.request();

        }
    }


}
