package com.ysy15350.startcarunion.store;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.GridViewAdpater_CarType;
import com.ysy15350.startcarunion.store.cartype_tabs.CarTypeTab1Fragment;
import com.ysy15350.startcarunion.store.cartype_tabs.CarTypeTab2Fragment;
import com.ysy15350.startcarunion.store.cartype_tabs.CarTypeTab3Fragment;
import com.ysy15350.startcarunion.store.cartype_tabs.CarTypeTab4Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import api.base.model.Response;
import api.model.CarType;
import base.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.JsonConvertor;

@ContentView(R.layout.activity_car_type)
public class CarTypeActivity extends MVPBaseActivity<CarTypeViewInterface, CarTypePresenter>
        implements CarTypeViewInterface {

    @ViewInject(R.id.container)
    private ViewPager mViewPager;

    @ViewInject(R.id.ll_tab1)
    private View ll_tab1;
    @ViewInject(R.id.ll_tab2)
    private View ll_tab2;
    @ViewInject(R.id.ll_tab3)
    private View ll_tab3;
    @ViewInject(R.id.ll_tab4)
    private View ll_tab4;


    @ViewInject(R.id.cursor)
    private ImageView cursor;// 动画图片

    private int screenW = 0;
    private int offset = 0;// 动画图片偏移量
    private int bmpW;// 动画图片宽度

    /**
     * 显示指定选项卡
     */
    public int tab_position = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected CarTypePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new CarTypePresenter(CarTypeActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //----------解决被回收无法切换问题-------
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        //----------解决被回收无法切换问题end-------
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initView() {

        super.initView();

        setFormHead("选择汽车品牌");


        try {
            bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.line_tab).getWidth();// 获取图片宽度

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            screenW = dm.widthPixels;// 获取分辨率宽度
            offset = screenW / 4 - bmpW;// 计算偏移量
            final Matrix matrix = new Matrix();
            matrix.postTranslate(offset, 0);

            cursor.setImageMatrix(matrix);// 设置动画初始位置

            // cursor.setDrawingCacheEnabled(true);// 获取bm前执行，否则无法获取
            // Bitmap bm = cursor.getDrawingCache();
            // int bm_width = bm.getWidth();
            // int widht_1 = screenW / 4;


            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            //tabLayout.setupWithViewPager(mViewPager);


            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setTab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTab(int position) {
        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面
        switch (position) {
            case 0:
                setTabImage(position, ll_tab1);
                break;
            case 1:
                setTabImage(position, ll_tab2);
                break;
            case 2:
                setTabImage(position, ll_tab3);
                break;
            case 3:
                setTabImage(position, ll_tab4);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        getCarType();

        setSelect(tab_position);
        setTab(tab_position);
    }

    public void getCarType() {
        try {
            String carTypeTopJson = BaseData.getCache("carTypeTop");
            if (!CommFunAndroid.isNullOrEmpty(carTypeTopJson)) {
                List<CarType> list = JsonConvertor.fromJson(carTypeTopJson, new TypeToken<List<CarType>>() {
                }.getType());
                bindTopCarType(list);
            } else {
                CommFunMessage.showWaitDialog(this, "数据加载中...");
                mPresenter.pro_type(0);// 获取第一级类型
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTabImage(int position, View view) {

        setView(view);

        int one = offset + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        switch (position) {
            case 3:
                one = one + 15;
                break;

            default:
                break;
        }

        Animation animation = new TranslateAnimation(one * tab_position, one * position, 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        cursor.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //setSelect(tab_position);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tab_position = position;


    }


    /**
     * 点击事件1:设置tab(改变图片和字体)和2:切换fragment
     *
     * @param position
     */
    protected void setSelect(int position) {

        getCarType();

        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面

        mViewPager.setCurrentItem(position);


    }


    /**
     * 记录当前view（图片切换）
     */
    private View currentView;

    /**
     * 切换图片（background 设置背景：xml->selector）
     *
     * @param v
     */
    private void setView(View v) {

        if (currentView != null) {
            if (currentView.getId() != v.getId()) {
                View imgview = currentView.findViewWithTag("tabimg");
                View textview = currentView.findViewWithTag("tabtext");
                if (imgview != null)
                    imgview.setEnabled(true);
                if (textview != null) {
                    textview.setEnabled(true);
                }
            }
        }
        if (v != null) {
            View imgview = v.findViewWithTag("tabimg");
            View textview = v.findViewWithTag("tabtext");
            if (imgview != null)
                imgview.setEnabled(false);
            if (textview != null) {
                textview.setEnabled(false);
            }
        }
        currentView = v;
    }

    @Event(value = R.id.ll_tab1)
    private void ll_tab1Click(View view) {
        setSelect(0);
        //setTabImage(0, view);

    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {
        setSelect(1);
    }

    @Event(value = R.id.ll_tab3)
    private void ll_tab3Click(View view) {
        setSelect(2);
    }

    @Event(value = R.id.ll_tab4)
    private void ll_tab4Click(View view) {
        setSelect(3);
    }

    List<CarType> mCarTypeToplist;

    @Override
    public void bindTopType(Response response) {
        // TODO Auto-generated method stub

        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                BaseData.setCache("carTypeTop", response.getResultJson(), 3600);
                List<CarType> list = response.getData(new TypeToken<List<CarType>>() {
                }.getType());

                bindTopCarType(list);

            }
        }

    }

    private void bindTopCarType(List<CarType> list) {

        try {
            if (list != null && !list.isEmpty()) {

                mCarTypeToplist = list;

                for (int i = 0; i < list.size(); i++) {
                    CarType carType = list.get(i);
                    switch (i) {
                        case 0:
                            mHolder.setText(R.id.tv_tab1, carType.getTitle());
                            break;
                        case 1:
                            mHolder.setText(R.id.tv_tab2, carType.getTitle());
                            break;
                        case 2:
                            mHolder.setText(R.id.tv_tab3, carType.getTitle());
                            break;
                        case 3:
                            mHolder.setText(R.id.tv_tab4, carType.getTitle());
                            break;

                        default:

                            break;
                    }
                }

                fragment1.onResume();
                fragment2.onResume();
                fragment3.onResume();
                fragment4.onResume();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    GridViewAdpater_CarType mAdapter;


    @Override
    protected void onDestroy() {
        try {
            if (mAdapter != null)
                mAdapter.destoryImageView();
            mAdapter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.gc();
        super.onDestroy();


    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    // /**
    // * 登录按钮点击事件
    // *
    // * @param view
    // */
    // @Event(value = R.id.btn_login)
    // private void btn_loginClick(View view) {
    // String userName = mHolder.getViewText(R.id.et_userName);
    // String password = mHolder.getViewText(R.id.et_password);
    // // String code = mHolder.getViewText(R.id.et_code);
    // if (CommFunAndroid.isNullOrEmpty(userName)) {
    // CommFunMessage.showMsgBox(this, "请输入用户名");
    // return;
    // }
    //
    // if (CommFunAndroid.isNullOrEmpty(password)) {
    // CommFunMessage.showMsgBox(this, "请输入密码");
    // return;
    // }
    //
    // mPresenter.login(userName, password);
    //
    // // mPresenter.getData();//
    // // 调用presenter的获取数据方法，在presenter类中调用bindData接口，本类实现了bindData方法
    // //
    // }

    CarTypeTab1Fragment fragment1;
    CarTypeTab2Fragment fragment2;
    CarTypeTab3Fragment fragment3;
    CarTypeTab4Fragment fragment4;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    fragment1 = new CarTypeTab1Fragment();
                    return fragment1;
                }
                case 1: {
                    fragment2 = new CarTypeTab2Fragment();
                    return fragment2;
                }
                case 2: {
                    fragment3 = new CarTypeTab3Fragment();
                    return fragment3;
                }
                case 3: {
                    fragment4 = new CarTypeTab4Fragment();
                    return fragment4;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

}
