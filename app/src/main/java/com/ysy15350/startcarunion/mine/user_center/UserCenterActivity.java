package com.ysy15350.startcarunion.mine.user_center;

import android.content.Intent;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Config;
import api.base.model.Response;
import base.BaseData;
import base.config.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;

/**
 * 我的
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_user_center)
public class UserCenterActivity extends MVPBaseActivity<UserCenterViewInterface, UserCenterPresenter>
        implements UserCenterViewInterface {


    @Override
    protected UserCenterPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new UserCenterPresenter(UserCenterActivity.this);
    }

//    DataBinding
//
//    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0603/2992.html
//
//    http://www.jianshu.com/p/9c99a4bf7c9d
//
//    http://www.jianshu.com/p/ba4982be30f8


    @Override
    public void initView() {

        super.initView();

        setFormHead("个人中心");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseData.getInstance().getUid() != 0) {
            showWaitDialog("数据加载中...");
            mPresenter.user_info();
        }
        bindData();
    }

    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    UserInfo userInfo = response.getData(UserInfo.class);
                    userInfo.setUid(BaseData.getInstance().getUid());
                    BaseData.getInstance().setUserInfo(userInfo);

                    bindData();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindData() {
        // TODO Auto-generated method stub
        super.bindData();

        if (BaseData.getInstance().getUid() != 0) {

            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            if (userInfo != null) {

                int type = userInfo.getType();

                if (type == 1) {
                    mHolder.setVisibility_VISIBLE(R.id.ll_is_business);
                } else {
                    mHolder.setVisibility_GONE(R.id.ll_is_business);
                }

                if (!CommFunAndroid.isNullOrEmpty(userInfo.getLitpic())) {

                    mHolder.setImageURL(R.id.img_head,
                            Config.getServer() + userInfo.getLitpic(),
                            true);
                }


                mHolder.setVisibility_VISIBLE(R.id.tv_userName).setVisibility_GONE(R.id.ll_author);
                mHolder.setText(R.id.tv_userName, userInfo.getFullname());
            } else {
                mHolder.setVisibility_GONE(R.id.tv_userName).setVisibility_VISIBLE(R.id.ll_author);
            }

        } else {
            mHolder.setVisibility_GONE(R.id.tv_userName).setVisibility_VISIBLE(R.id.ll_author);
        }

    }

    /**
     * 头像点击事件
     *
     * @param view
     */
    @Event(value = R.id.img_head)
    private void img_headClick(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyInfoActivity.class));

    }

    /**
     * 登录
     *
     * @param view
     */
    @Event(value = R.id.tv_login)
    private void tv_loginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);

        finish();
    }

    /**
     * 注册
     *
     * @param view
     */
    @Event(value = R.id.tv_register)
    private void tv_registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
        this.finish();
    }

    /**
     * 设置
     *
     * @param view
     */
    @Event(value = R.id.ll_setting)
    private void ll_settingClick(View view) {
        Intent intent = new Intent(this, SettingActivity.class);

        startActivityForResult(intent, 1);
    }

    /**
     * 我的收藏
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        if (isLogin())
            startActivity(new Intent(this, MyCollectionActivity.class));

    }

    /**
     * 我的评论
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {


        if (isLogin())
            startActivity(new Intent(this, MyCommentActivity.class));
    }

    /**
     * 我的积分
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyScoreActivity.class));
    }

    /**
     * 我的足迹
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyFootprintActivity.class));

    }

    /**
     * 我的消息
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyMsgActivity.class));

    }

    // ---------------------------仅限商家可用---------------------------

    /**
     * 我的店铺
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, EditShopActivity.class));


    }

    /**
     * 我的访客
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_7)
    private void ll_menu_7Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyVisitorActivity.class));
    }

    /**
     * 我的联系人
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_8)
    private void ll_menu_8Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyContactsActivity.class));

    }

    // ---------------------------仅限商家可用end---------------------------

    /**
     * 我的资料
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_9)
    private void ll_menu_9Click(View view) {

        if (isLogin())
            startActivity(new Intent(this, MyInfoActivity.class));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    this.finish();
                }
                break;
            default:
                break;
        }
    }


}
