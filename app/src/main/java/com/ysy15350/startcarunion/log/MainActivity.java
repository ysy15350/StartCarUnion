package com.ysy15350.startcarunion.log;

import android.content.Intent;
import android.view.View;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import base.BaseActivity;

/**
 * Created by yangshiyou on 2017/9/16.
 */

@ContentView(R.layout.activity_main_tab3)
public class MainActivity extends BaseActivity {


    @Override
    public void initView() {
        super.initView();
        setFormHead("错误日志");
    }


    /**
     * 日志列表
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        startActivity(new Intent(this, LogListActivity.class));

    }

    /**
     * 闪退日志
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        startActivity(new Intent(this, ErrorLogActivity.class));

    }

    /**
     * 内存信息
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        startActivity(new Intent(this, MemoryInfoActivity.class));

    }

}
