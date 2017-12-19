package com.ysy15350.startcarunion.ask_price_platform;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Response;
import api.model.AskPricieInfo;
import base.mvp.MVPBaseActivity;
import custom_view.dialog.ReplyDialog;


@ContentView(R.layout.activity_ask_detail)
public class AskPriceDetailActivity extends MVPBaseActivity<AskPriceDetailViewInterface, AskPriceDetailPresenter>
        implements AskPriceDetailViewInterface {

    @ViewInject(R.id.et_tent)
    private EditText et_tent;

    @ViewInject(R.id.img_ad)
    private ImageView img_ad;


    @Override
    protected AskPriceDetailPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new AskPriceDetailPresenter(AskPriceDetailActivity.this);
    }

    private int mId;


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mId = intent.getIntExtra("ask_id", -1);

        if (mId != -1) {
            mPresenter.inquiry_info(mId);
        }

    }

    @Override
    public void inquiry_infoCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    AskPricieInfo askPricieInfo = response.getData(AskPricieInfo.class);
                    bindStoreInfo(askPricieInfo);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("询价回复");
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    @Override
    public void initData() {
        super.initData();

    }

    private final static int BRAND = 101;


    private AskPricieInfo mAskPricieInfo;

    @Override
    public void bindData() {
        super.bindData();

        bindStoreInfo(mAskPricieInfo);
    }

    /**
     * 绑定详情信息
     */
    private void bindStoreInfo(AskPricieInfo askPricieInfo) {

        try {
            if (askPricieInfo != null) {
                mHolder.setText(R.id.tv_content, askPricieInfo.getContent());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add_inquiryCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String mAtlas = "";


    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        ReplyDialog dialog = new ReplyDialog(this);
        dialog.setDialogListener(new ReplyDialog.DialogListener() {
            @Override
            public void onCancelClick() {

            }

            @Override
            public void onOkClick(String content) {
                mPresenter.add_inquiry(mId, mAtlas, content);
            }
        });
        dialog.show();
    }


}
