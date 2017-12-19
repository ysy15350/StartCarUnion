package com.ysy15350.startcarunion.log;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;

import base.BaseActivity;
import common.CommFunAndroid;
import common.JsonConvertor;
import common.model.AppMemoryInfo;

/**
 * Created by yangshiyou on 2017/8/31.
 */

@ContentView(R.layout.activity_error_log)
public class MemoryInfoActivity extends BaseActivity {


    @Override
    public void initView() {
        super.initView();
        setFormHead("错误日志");
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            AppMemoryInfo memoryInfo = CommFunAndroid.getInstance().getAppMemoryInfo();
            if (memoryInfo != null) {
                String content = JsonConvertor.toJson(memoryInfo);
                mHolder.setText(R.id.tv_content, content);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        super.initData();

    }


}
