package com.ysy15350.startcarunion.store;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.GridViewAdpater_CarType;
import com.ysy15350.startcarunion.business.StoreInfoListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.CarType;
import base.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.JsonConvertor;

@ContentView(R.layout.activity_car_type)
public class CarTypeActivity2 extends MVPBaseActivity<CarTypeViewInterface, CarTypePresenter>
        implements CarTypeViewInterface {

    @ViewInject(R.id.cursor)
    private ImageView cursor;// 动画图片

    private int screenW = 0;
    private int offset = 0;// 动画图片偏移量
    private int bmpW;// 动画图片宽度

    /**
     * 显示指定选项卡
     */
    public static int tab_position = 0;

    @ViewInject(R.id.ll_tab1)
    private View ll_tab1;

    @ViewInject(R.id.gv_car)
    private GridView gv_car;

    @Override
    protected CarTypePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new CarTypePresenter(CarTypeActivity2.this);
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

            setTabImage(0, ll_tab1);

            gv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    if (item != null && item instanceof CarType) {
                        CarType carType = (CarType) item;
                        int id = carType.getId();
                        Intent intent = new Intent(CarTypeActivity2.this, StoreInfoListActivity.class);
                        intent.putExtra("cartype_id", id);
                        startActivity(intent);
                        showMsg(carType.getTitle());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        try {
            String carTypeTopJson = BaseData.getCache("carTypeTop");
            if (!CommFunAndroid.isNullOrEmpty(carTypeTopJson)) {
                List<CarType> list = JsonConvertor.fromJson(carTypeTopJson, new TypeToken<List<CarType>>() {
                }.getType());
                bindTopCarType(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        showWaitDialog("数据加载中...");
        mPresenter.pro_type(0);// 获取第一级类型
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


                getCarType(tab_position);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tab_position = position;


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
        setTabImage(0, view);

    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {
        setTabImage(1, view);
    }

    @Event(value = R.id.ll_tab3)
    private void ll_tab3Click(View view) {
        setTabImage(2, view);
    }

    @Event(value = R.id.ll_tab4)
    private void ll_tab4Click(View view) {
        setTabImage(3, view);
    }

    List<CarType> mCarTypeToplist;

    @Override
    public void bindTopType(Response response) {
        // TODO Auto-generated method stub

        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                BaseData.setCache("carTypeTop", response.getResultJson());
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
            }

            if (gv_car.getCount() == 0) {
                getCarType(tab_position);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getCarType(int index) {
        try {

            String carTypeJson = BaseData.getCache("carType" + tab_position);

            if (!CommFunAndroid.isNullOrEmpty(carTypeJson)) {
                List<CarType> list = JsonConvertor.fromJson(carTypeJson, new TypeToken<List<CarType>>() {
                }.getType());
                bindCarTypeGridView(list);
            } else {
                bindCarTypeGridView(null);
            }

            int pid = 0;

            if (mCarTypeToplist != null && !mCarTypeToplist.isEmpty() && mCarTypeToplist.size() > index) {
                CarType carType = mCarTypeToplist.get(index);
                if (carType != null) {
                    pid = carType.getId();
                }
            }

            if (pid != 0) {
//                CommFunMessage.showWaitDialog("数据加载中...");
                //mPresenter.getCarTypeList(pid);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    List<CarType> mCarTypelist;

    public void bindCarType(Response response) {

        CommFunMessage.hideWaitDialog();
        // TODO Auto-generated method stub
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                mHolder.setText(R.id.tv_result, response.getResult().toString());

                BaseData.setCache("carType" + tab_position, response.getResultJson());


                List<CarType> list = response.getData(new TypeToken<List<CarType>>() {
                }.getType());

                bindCarTypeGridView(list);

            }
        }
    }

    private void bindCarTypeGridView(List<CarType> list) {

        try {
            if (list != null && !list.isEmpty()) {
                mAdapter = new GridViewAdpater_CarType(this, list);

            } else {
                mAdapter = new GridViewAdpater_CarType(this, new ArrayList<CarType>());
            }
            gv_car.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    GridViewAdpater_CarType mAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mAdapter != null)
                mAdapter.destoryImageView();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
