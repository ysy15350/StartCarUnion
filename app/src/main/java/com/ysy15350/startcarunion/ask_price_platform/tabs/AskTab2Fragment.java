package com.ysy15350.startcarunion.ask_price_platform.tabs;

import android.view.View;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.ask_price_platform.AskPriceActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Response;
import base.BaseData;
import base.mvp.MVPBaseFragment;
import common.AndroidBug5497Workaround;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_ask_tab2)
public class AskTab2Fragment extends MVPBaseFragment<AskTab2ViewInterface, AskTab2Presenter>
        implements AskTab2ViewInterface {


    @Override
    public AskTab2Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new AskTab2Presenter(getActivity());
    }

    @Override
    public void initView() {
        super.initView();
        AndroidBug5497Workaround.assistActivity(getActivity());//解决底部输入框键盘遮挡问题
        setFormHead("发布询价");
    }

    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {
        int sid = CommFunAndroid.toInt32(BaseData.getInstance().getUid(), -1);
        String atlas = "";
        String content = mHolder.getViewText(R.id.et_tent);
        if (CommFunAndroid.isNullOrEmpty(content)) {
            showMsg("你还没有输入内容哦");
            return;
        }
        mPresenter.add_inquiry(0, atlas, content);
    }

    @Override
    public void add_inquiryCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mHolder.setText(R.id.et_tent, "");

                    ((AskPriceActivity) getActivity()).setSelect(2);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            CommFunAndroid.hideSoftInput(mHolder.getView(R.id.et_tent));
        }
    }

    @Override
    protected void back() {
        super.back();
        getActivity().finish();
    }
}
