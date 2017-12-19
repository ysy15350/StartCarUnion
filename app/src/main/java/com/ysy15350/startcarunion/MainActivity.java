package com.ysy15350.startcarunion;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.base.BaseFragmentActivity;
import com.ysy15350.startcarunion.mine.SettingActivity;
import com.ysy15350.startcarunion.mine.my_call.MyCallActivity;
import com.ysy15350.startcarunion.mine.my_collection.MyCollectionActivity;
import com.ysy15350.startcarunion.mine.my_comment.MyCommentActivity;
import com.ysy15350.startcarunion.mine.my_contacts.MyContactsActivity;
import com.ysy15350.startcarunion.mine.my_footprint.MyFootprintActivity;
import com.ysy15350.startcarunion.mine.my_info.MyInfoActivity;
import com.ysy15350.startcarunion.mine.my_msg.MyMsgActivity;
import com.ysy15350.startcarunion.mine.my_score.MyScoreActivity;
import com.ysy15350.startcarunion.mine.my_shop.EditShopActivity;
import com.ysy15350.startcarunion.mine.my_visitor.MyVisitorActivity;
import com.ysy15350.startcarunion.mine.user_center.UserCenterActivity;
import com.ysy15350.startcarunion.transaction.ContactsUsActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;

import api.base.model.Config;
import base.BaseData;
import base.config.entity.UserInfo;
import common.CommFunAndroid;

@ContentView(R.layout.activity_main_slide)
public class MainActivity extends BaseFragmentActivity {

    @Override
    public void initView() {
        super.initView();
        //设置滑动边距
        setDrawerLeftEdgeSize(this, mDrawerlayout, 0.8f);
    }

    @Override
    protected void onResume() {
        super.onResume();


        checkAppStatus();

        bindUserInfo();

    }

    @ViewInject(R.id.id_drawerlayout)
    private DrawerLayout mDrawerlayout;


    @ViewInject(R.id.id_drawer)
    private LinearLayout id_drawer;


    @Event(value = R.id.ll_pop_menu)
    private void ll_pop_menuClick(View view) {
        toggleMenu();
    }

    public static int state = 0;

    public void toggleMenu() {

        if (state == 0) {
            mDrawerlayout.openDrawer(id_drawer);
            state = 1;
        } else {
            mDrawerlayout.closeDrawer(id_drawer);
            state = 0;
        }

//        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_down_1);
//
//        if (state == 0) {
//
//        } else {
//            loadAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up_1);
//        }
//
//        ll_pop_menu.startAnimation(loadAnimation);
//
//        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // TODO Auto-generated method stub
//                if (state == 1) {
//                    state = 0;
//                    mHolder.setVisibility_GONE(R.id.ll_pop_menu);
//                } else {
//                    state = 1;
//                    mHolder.setVisibility_VISIBLE(R.id.ll_pop_menu);
//                }
//            }
//        });

    }

    @Override
    public void bindData() {
        super.bindData();


//        if (BaseData.getInstance().getUid() != 0) {
//            UserInfo userInfo = BaseData.getInstance().getUserInfo();
//            if (userInfo != null) {
//                if (!CommFunAndroid.isNullOrEmpty(userInfo.getLitpic())) {
//
//                    mHolder.setImageURL(R.id.img_head_pop,
//                            Config.getServer() + userInfo.getLitpic(),
//                            true).setText(R.id.tv_userName_pop, userInfo.getFullname());
//                }
//            }
//        }
    }

    public void bindUserInfo() {
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

                    mHolder.setImageURL(R.id.img_head_pop,
                            Config.getServer() + userInfo.getLitpic(), 130, 130,
                            true);
                }
                mHolder.setText(R.id.tv_userName_pop, userInfo.getFullname());

            } else {
                mHolder.setImageResource(R.id.img_head_pop,
                        R.mipmap.icon_head
                ).setText(R.id.tv_userName_pop, "登录");
            }

        }
    }


    @Event(value = R.id.btn_setting)
    private void btn_settingClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, SettingActivity.class));
        toggleMenu();
    }

    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.img_head_pop)
    private void img_head_popClick(View view) {
//        if (isLogin())
//            startActivity(new Intent(this, UserCenterActivity.class));
//        toggleMenu();

        if (isLogin())
            startActivity(new Intent(this, MyInfoActivity.class));
        toggleMenu();
    }

    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.tv_userName)
    private void tv_userNameClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, MyInfoActivity.class));
        toggleMenu();
    }

    /**
     * 我的店铺
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        if (isLogin())
            startActivity(new Intent(this, EditShopActivity.class));
        toggleMenu();
    }

    /**
     * 我的访客
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        if (isLogin())
            startActivity(new Intent(this, MyVisitorActivity.class));
        toggleMenu();
    }

    /**
     * 我的联系人
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        if (isLogin())
            startActivity(new Intent(this, MyContactsActivity.class));
        toggleMenu();
    }

    /**
     * 我的积分
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        if (isLogin())
            startActivity(new Intent(this, MyScoreActivity.class));
        toggleMenu();
    }

    /**
     * 我的足迹
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {
        startActivity(new Intent(this, MyFootprintActivity.class));
        toggleMenu();
    }

    /**
     * 我的收藏
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {
        startActivity(new Intent(this, MyCollectionActivity.class));
        toggleMenu();
    }


    /**
     * 我的评论
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_7)
    private void ll_menu_7Click(View view) {
        startActivity(new Intent(this, MyCommentActivity.class));
        toggleMenu();
    }

    /**
     * 已拨电话
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_8)
    private void ll_menu_8Click(View view) {
        startActivity(new Intent(this, MyCallActivity.class));
        toggleMenu();
    }


    /**
     * 设置抽屉菜单滑动边距
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }


}
