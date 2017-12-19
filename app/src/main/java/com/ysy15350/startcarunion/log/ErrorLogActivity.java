package com.ysy15350.startcarunion.log;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;

import java.io.File;

import base.BaseActivity;
import common.CommFunAndroid;
import common.FileUtils;

/**
 * Created by yangshiyou on 2017/8/31.
 */

@ContentView(R.layout.activity_error_log)
public class ErrorLogActivity extends BaseActivity {


    @Override
    public void initView() {
        super.initView();
        setFormHead("错误日志");
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            File file = new File(CommFunAndroid.getDiskCachePath() + "/aandroid_log/error");
            if (file != null && file.exists()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {

                    String content = "";

                    for (File file_item :
                            files) {
                        if (file_item != null && file_item.exists() && file_item.isFile()) {
                            content = content + "\n---------" + FileUtils.readFile(file_item.getPath());

                        }
                    }

                    mHolder.setText(R.id.tv_content, content);

                }
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
