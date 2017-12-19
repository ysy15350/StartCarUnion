package com.ysy15350.startcarunion.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.StartActivity;
import com.ysy15350.startcarunion.login.LoginActivity;
import com.ysy15350.startcarunion.main_tabs.MainTab1Fragment;
import com.ysy15350.startcarunion.main_tabs.MainTab2Fragment;
import com.ysy15350.startcarunion.main_tabs.MainTab3Fragment;
import com.ysy15350.startcarunion.service.IncomingCallService;
import com.ysy15350.startcarunion.service.MyService;
import com.ysy15350.startcarunion.service.OutgoingCallService;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import base.BaseData;
import base.BaseFragment;
import base.IView;
import base.adapter.ViewHolder;
import common.AppStatusManager;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.ExitApplication;
import common.model.ScreenInfo;


/**
 * Created by yangshiyou on 2017/8/30.
 */

public class BaseFragmentActivity extends FragmentActivity implements IView {

    protected final static String TAG = "StartCarUnion";

    /**
     * 控件ViewGroup
     */
    protected View mContentView;

    protected ViewHolder mHolder;


    @ViewInject(R.id.id_content)
    private LinearLayout id_content;

    @ViewInject(R.id.ll_tab1)
    private View ll_tab1;

    @ViewInject(R.id.ll_tab2)
    private View ll_tab2;

    @ViewInject(R.id.ll_tab3)
    private View ll_tab3;

    private Fragment fragmentTab1;
    private Fragment fragmentTab2;
    private Fragment fragmentTab3;

    /**
     * 显示指定选项卡
     */
    public static int tab_position = 0;


    Intent myServiceIntent;
    Intent inComingCallItent;
    Intent outGogingCallIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //----------解决被回收无法切换问题-------
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        //----------解决被回收无法切换问题end-------
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        if (savedInstanceState != null) {
            //showMsg("内存不足");
            //界面在内存不足情况下被强制回收后重新create的逻辑
        } else {
            //界面正常情况下create时的逻辑
            //showMsg("正常");
        }


        mContentView = getWindow().getDecorView();

        mHolder = ViewHolder.get(this, mContentView);

        // String className = this.getClass().getName();

        // showMsg(className);

        myServiceIntent = new Intent(this, MyService.class);
        inComingCallItent = new Intent(this, IncomingCallService.class);
        outGogingCallIntent = new Intent(this, OutgoingCallService.class);

        startService(myServiceIntent);
        startService(inComingCallItent);
        startService(outGogingCallIntent);

        init();

    }


    @Override
    protected void onResume() {
        super.onResume();

        setSelect(tab_position);

        // showMsg("currentFragment==null?" + (currentFragment == null));

        if (currentFragment != null)
            showFragment(currentFragment, mIsHideBottom);
    }

    /**
     * 初始化，1：initView；2：readCahce；3：loadData；4：bindData
     */
    private void init() {

        initView();

        initData();


        readCahce();

        loadData();

        bindData();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        CommFunAndroid.fullScreenStatuBar(this);
    }

    @Override
    public void readCahce() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void bindData() {

    }

    @Override
    public void showMsg(String msg) {
        if (msg == null)
            return;
        CommFunMessage.showMsgBox(this, msg);
    }

    @Override
    public void showWaitDialog(String msg) {
        if (CommFunAndroid.isNullOrEmpty(msg))
            return;
        CommFunMessage.showWaitDialog(this, msg);
    }

    @Override
    public void hideWaitDialog() {
        CommFunMessage.hideWaitDialog();
    }


    @Override
    public void setViewText(int id, CharSequence text) {
        if (mHolder != null)
            mHolder.setText(id, text);
    }

    @Override
    public String getViewText(int id) {
        if (mHolder != null)
            return mHolder.getViewText(id);
        return "";
    }

    @Override
    public void setTextColor(int id, int color) {
        if (mHolder != null)
            mHolder.setTextColor(id, color);
    }

    @Override
    public void setBackgroundColor(int id, int color) {
        if (mHolder != null)
            mHolder.setBackgroundColor(id, color);
    }

    @Override
    public void setVisibility_GONE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_GONE(id);
    }

    @Override
    public void setVisibility_VISIBLE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_VISIBLE(id);

    }

    @Event(value = R.id.ll_tab1)
    private void ll_tab1Click(View view) {
        setSelect(0);
    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {
        if (isLogin())
            setSelect(1);
    }

    @Event(value = R.id.ll_tab3)
    private void ll_tab3Click(View view) {

        setSelect(2);

    }

    FragmentTransaction transaction;

    /**
     * 点击事件1:设置tab(改变图片和字体)和2:切换fragment
     *
     * @param position
     */
    protected void setSelect(int position) {


        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面

        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();

        //List<Fragment> fm_fragments = fm.getFragments();

        // hideFragment(fm_fragments, transaction);
        hideFragment(transaction);


        switch (position) {
            case 0:
                if (fragmentTab1 == null) {
                    fragmentTab1 = new MainTab1Fragment();
                    transaction.add(R.id.id_content, fragmentTab1);
                }
                transaction.show(fragmentTab1);
                break;
            case 1:

                if (fragmentTab2 == null) {
                    fragmentTab2 = new MainTab2Fragment();
                    transaction.add(R.id.id_content, fragmentTab2);
                }
                transaction.show(fragmentTab2);
                break;
            case 2:
                if (fragmentTab3 == null) {
                    fragmentTab3 = new MainTab3Fragment();
                    transaction.add(R.id.id_content, fragmentTab3);

                }
                transaction.show(fragmentTab3);

                break;

            default:
                break;
        }

        transaction.commit();

        setTab(position);
    }

    private void hideFragment(List<Fragment> fragments, FragmentTransaction transaction) {

        if (fragments != null && !fragments.isEmpty()) {
            // showMsg("fragments:" + fragments.size());
            for (Fragment fragment : fragments) {
                transaction.hide(fragment);
            }
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentTab1 != null)
            transaction.hide(fragmentTab1);
        if (fragmentTab2 != null)
            transaction.hide(fragmentTab2);
        if (fragmentTab3 != null)
            transaction.hide(fragmentTab3);
    }

    /**
     * 显示指定Fragment
     *
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        try {

            currentFragment = fragment;

            /**
             * 1、获取fragment类名； 2、在FragmentManager中查找对应的tag名称；
             * 3、如果找到对应的fragment，则无需添加新的fragment； 4、如果未找到，则添加新的fragment;
             */

            String fragmentClassName = fragment.getClass().getName();// 1、获取fragment类名；

            FragmentManager fm = getSupportFragmentManager();

            List<Fragment> fm_fragments = fm.getFragments();// 获取当前所有的fragment集合

            Fragment fm_fragment = fm.findFragmentByTag(fragmentClassName);// 2、在FragmentManager中查找对应的tag名称；

            if (fm_fragment == null)
                fm_fragment = fragment;// 4、如果未找到，则添加新的fragment

            transaction = fm.beginTransaction();
            hideFragment(fm_fragments, transaction);

            if (!fm_fragments.contains(fm_fragment)) {
                showMsg("添加" + fragmentClassName);
                // transaction.replace(R.id.id_content, fragment);
                transaction.replace(R.id.id_content, fm_fragment, fm_fragment.getClass().getName());// 把类名作为tag名称
            } else {
                transaction.show(fm_fragment);
                fm_fragment.onResume();
            }

            transaction.commit();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static BaseFragment currentFragment;

    private static boolean mIsHideBottom;

    /**
     * 显示指定Fragment
     *
     * @param fragment
     * @param hiddenBottom 是否隐藏底部，true:隐藏
     */
    public void showFragment(BaseFragment fragment, boolean hiddenBottom) {

        showFragment(fragment);

        mIsHideBottom = hiddenBottom;
        if (mIsHideBottom)
            mHolder.setVisibility_GONE(R.id.form_bottom);
        else
            mHolder.setVisibility_VISIBLE(R.id.form_bottom);
    }

    /**
     * 返回到主界面，指定选项卡
     *
     * @param index 选项卡
     */
    public void backFragment() {
        currentFragment = null;// 当前显示fragment置空
        mHolder.setVisibility_VISIBLE(R.id.form_bottom);// 显示底部菜单
        setSelect(tab_position);
    }

    /**
     * 滑动viewpager时设置tab(改变图片和字体)
     *
     * @param position
     */
    private void setTab(int position) {
        switch (position) {
            case 0:
                setViewImage(ll_tab1);
                break;
            case 1:
                setViewImage(ll_tab2);
                break;
            case 2:
                setViewImage(ll_tab3);
                break;

            default:
                break;
        }
    }

    // ----------------------------------------
    // 切换图片方式二：xml配置 drawable selector

    /**
     * 记录当前view（图片切换）
     */
    private View currentView;
    /**
     * 记录当前textview(切换字体颜色)
     */
    private View currentTeView;

    /**
     * 切换图片（background 设置背景：xml->selector）
     *
     * @param v
     */
    private void setViewImage(View v) {
        if (currentView != null) {
            if (currentView.getId() != v.getId()) {
                View imgview = currentView.findViewWithTag("tab_img");
                View textview = currentView.findViewWithTag("tab_txt");
                if (imgview != null)
                    imgview.setEnabled(true);
                if (textview != null) {
                    textview.setEnabled(true);
                }
            }
        }
        if (v != null) {
            View imgview = v.findViewWithTag("tab_img");
            View textview = v.findViewWithTag("tab_txt");
            if (imgview != null)
                imgview.setEnabled(false);
            if (textview != null) {
                textview.setEnabled(false);
            }
        }
        currentView = v;
    }

    /**
     * 点击返回按钮间隔时间
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ScreenInfo screenInfo = CommFunAndroid.getScreenInfo(this);
            showMsg(String.format("%f,%d,%d", screenInfo.getDensity(), screenInfo.getWidth(), screenInfo.getHeight()));
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (currentFragment != null)
                    backFragment();
                else {
                    int myPid = android.os.Process.myPid();
                    showMsg("再按一次退出程序");

                    exitTime = System.currentTimeMillis();
                }

            } else {
                stopService(myServiceIntent);// stop

                // CommFunAndroid.setSharedPreferences("JSESSIONID", "");

                ExitApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(myServiceIntent);
        stopService(inComingCallItent);
        stopService(outGogingCallIntent);
    }

    protected boolean isLogin() {
        if (CommFunAndroid.isNullOrEmpty(BaseData.getInstance().getUid())) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);

            return false;
        }

        return true;
    }

    protected void checkAppStatus() {
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            //该应用已被回收，应用启动入口SplashActivity，走重启流程
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //showMsg("应用被回收重新执行");
        }
    }
}