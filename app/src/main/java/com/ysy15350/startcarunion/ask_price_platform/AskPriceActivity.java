package com.ysy15350.startcarunion.ask_price_platform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.ask_price_platform.tabs.AskTab1Fragment;
import com.ysy15350.startcarunion.ask_price_platform.tabs.AskTab2Fragment;
import com.ysy15350.startcarunion.ask_price_platform.tabs.AskTab3Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import base.BaseFragment;
import base.adapter.ViewHolder;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.ExitApplication;

/**
 * Created by yangshiyou on 2017/9/6.
 */

@ContentView(R.layout.activity_main_ask_price_platform)
public class AskPriceActivity extends FragmentActivity {

    /**
     * 控件ViewGroup
     */
    protected View mContentView;

    protected ViewHolder mHolder;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        mContentView = getWindow().getDecorView();

        mHolder = ViewHolder.get(this, mContentView);

        CommFunAndroid.fullScreenStatuBar(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelect(tab_position);
    }

    @Event(value = R.id.ll_tab1)
    private void ll_tab1Click(View view) {
        setSelect(0);

    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {
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
    public void setSelect(int position) {


        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面

        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();

        //List<Fragment> fm_fragments = fm.getFragments();

        // hideFragment(fm_fragments, transaction);
        //hideFragment(transaction);


        switch (position) {
            case 0:
                if (fragmentTab1 == null) {
                    fragmentTab1 = new AskTab1Fragment();

                }
                transaction.replace(R.id.id_content, fragmentTab1);
                break;
            case 1:
                if (fragmentTab2 == null) {
                    fragmentTab2 = new AskTab2Fragment();

                }
                transaction.replace(R.id.id_content, fragmentTab2);
                break;
            case 2:
                if (fragmentTab3 == null) {
                    fragmentTab3 = new AskTab3Fragment();

                }
                transaction.replace(R.id.id_content, fragmentTab3);

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


}
