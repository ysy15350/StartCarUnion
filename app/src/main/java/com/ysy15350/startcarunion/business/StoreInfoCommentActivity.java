package com.ysy15350.startcarunion.business;

import android.content.Intent;
import android.view.View;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Response;
import base.mvp.MVPBaseActivity;


@ContentView(R.layout.activity_store_info_comment)
public class StoreInfoCommentActivity extends MVPBaseActivity<StoreInfoCommentViewInterface, StoreInfoCommentPresenter>
        implements StoreInfoCommentViewInterface {


    private int mUid;
    private String mPid;

    @Override
    protected StoreInfoCommentPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new StoreInfoCommentPresenter(StoreInfoCommentActivity.this);
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("评论");
        Intent intent = getIntent();
        mUid = intent.getIntExtra("id", -1);
        mPid = intent.getStringExtra("pid");
    }

    @Override
    public void store_commentCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    this.finish();
                }


                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {
        try {
            String content = mHolder.getViewText(R.id.et_content).trim();
            mPresenter.store_comment(mUid, 1, content, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
