package com.ysy15350.startcarunion.main_tabs;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.MainActivity;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.ask_price_platform.AskPriceActivity;
import com.ysy15350.startcarunion.business.StoreInfoDetailActivity;
import com.ysy15350.startcarunion.business.StoreInfoListActivity;
import com.ysy15350.startcarunion.dialog.SignSuccessDialog;
import com.ysy15350.startcarunion.store.CarTypeActivity;
import com.ysy15350.startcarunion.store.CityListActivity;
import com.ysy15350.startcarunion.transaction.TransactionActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import api.model.Banner;
import base.BaseData;
import base.config.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFunAndroid;
import common.JsonConvertor;
import custom_view.SlideShowViewAuto;
import custom_view.TextSwitchView;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab1)
public class MainTab1Fragment extends MVPBaseFragment<MainTab1ViewInterface, MainTab1Presenter>
        implements MainTab1ViewInterface {

    private static int count = 0;

    public MainTab1Fragment() {
    }


    @Override
    public MainTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab1Presenter(getActivity());
    }


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();


        initBanner();

    }


    @Override
    public void loadData() {
        super.loadData();
        //BaseData.setCache("banne
        String bannerJson = BaseData.getCache("bannerJson");
        if (!CommFunAndroid.isNullOrEmpty(bannerJson)) {
            List<Banner> bannerList = JsonConvertor.fromJson(bannerJson, new TypeToken<List<Banner>>() {
            }.getType()); //response.getData(new TypeToken<List<Banner>>() {
            //  }.getType());
            bindBanner(bannerList);
        }

    }

    @Override
    public void initData() {
        super.initData();

        //showMsg("initData" + count++);

        String result = CommFunAndroid.getSharedPreferences("qd_welcomeStr");

        setTextSwitch(result);

        mPresenter.getBanner();//banner获取
        mPresenter.qd_welcome();//公告
    }

    // ----------------banner--------------------------------

    /**
     * banner
     */
    @ViewInject(R.id.slideshowView)
    private SlideShowViewAuto slideShowViewAuto;

    /**
     * banner 打开类型（url或指定界面和详情id）
     */
    HashMap<String, Banner> openInfo = new HashMap<String, Banner>();

    /**
     * 活动列表>1
     */
    List<Banner> mBanners = new ArrayList<Banner>();

    /**
     * banner image url集合>2
     */
    List<String> imageUrls = new ArrayList<String>();

    /**
     * banner 超链接<img_url,link_url>3
     */
    HashMap<String, String> imagesMap = new HashMap<String, String>();

    // --------------banner end----------------------------------

    private void initBanner() {
        // 滚动banner

        if (slideShowViewAuto != null) {

            slideShowViewAuto.setSlideClickListener(new SlideShowViewAuto.SlideClickListener() {

                @Override
                public void imgeClick(String imgurl, String linkurl) {

                }

                @Override
                public void imageClick(String imgurl, Banner banner) {
                    // TODO Auto-generated method stub

                    if (banner != null) {

                        String url = banner.getUrl();

                        if (CommFunAndroid.isInteger(url)) {//商家详情id
                            int id = CommFunAndroid.toInt32(url, -1);
                            Intent intent = new Intent(mContext, StoreInfoDetailActivity.class);
                            intent.putExtra("id", id);
                            //intent.putExtra("fullname", storeInfo.getFullname());//显示标题
                            startActivity(intent);
                        }

                        //int openType = banner.getOpentype();

                        //String openValue = CommFunAndroid.toString(banner.getOpenvalue());
                        //showMsg(openType + openValue);
//                        if (!CommFunAndroid.isNullOrEmpty(openValue)) {
//
//                            switch (openType) {
//                                case 1:// 打开网页
//                                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                                    intent.putExtra("param", openValue);
//                                    startActivity(intent);
//                                    break;
//                                case 2:// 打开电影详情
//
//                                    // 1、需要新增接口查询电影详情
//                                    // 2、活动表新增图片高度比例，用于计算banner高度
//                                    showMsg("打开activity");
//
//                                    // Intent intent1 = new Intent(getActivity(),
//                                    // MovieInviteActivity.class);
//                                    // intent1.putExtra("movieId",
//                                    // CommFunAndroid.toString(banner.getOpenvalue()));
//                                    // startActivity(intent1);
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
                    }
                }

            });


        }
    }

    @Override
    public void bindData() {
        // TODO Auto-generated method stub
        super.bindData();

        if (BaseData.getInstance().getUid() != 0) {
            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            if (userInfo != null) {
                if (!CommFunAndroid.isNullOrEmpty(userInfo.getLitpic())) {

                    mHolder.setImageURL(R.id.img_head,
                            Config.getServer() + userInfo.getLitpic(),
                            65, 65, true);
                }
            }
        }

    }

    @Override
    public void home_banaerCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("bannerJson", response.getResultJson());
                    List<Banner> bannerList = response.getData(new TypeToken<List<Banner>>() {
                    }.getType());
                    bindBanner(bannerList);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindBanner(List<Banner> banners) {
        // TODO Auto-generated method stub
        mBanners = banners;
        bindBanner();
    }

    /**
     * 绑定banner
     */
    private void bindBanner() {

        if (mBanners != null && mBanners.size() > 0) {

            imageUrls = new ArrayList<String>();
            openInfo = new HashMap<String, Banner>();

            for (Banner banner : mBanners) {
                if (banner != null && !CommFunAndroid.isNullOrEmpty(banner.getUrl())) {
                    imageUrls.add(Config.getServer() + CommFunAndroid.toString(banner.getIcon()));
                    openInfo.put(Config.getServer() + CommFunAndroid.toString(banner.getIcon()), banner);
                }
            }
        }

        if (slideShowViewAuto != null && imageUrls != null && imageUrls.size() > 0) {

            // ScreenInfo screenInfo =
            // CommFunAndroid.GetInstance(getActivity()).getScreenInfo();
            //
            // if (screenInfo != null) {//
            // slideShowViewAuto.setVisibility(View.GONE);设置无效，所以默认高度为0，如果有值改变view高度让控件可见
            //
            // int width = screenInfo.getWidth();
            // int height = screenInfo.getHeight();
            //
            // LayoutParams slideshowViewParams =
            // slideShowViewAuto.getLayoutParams();
            //
            // slideshowViewParams.height = (int) (width * 0.28);
            // }

            slideShowViewAuto.setVisibility(View.VISIBLE);

            // slideshowView.setLoadingImageResId(userSexImgResId);
            // slideshowView.setLoadfailImageResid(userSexImgResId);


            slideShowViewAuto.setImageUrls(imageUrls);
            slideShowViewAuto.setImagesMap(imagesMap);
            slideShowViewAuto.setOpenInfos(openInfo);

        } else {
            slideShowViewAuto.setVisibility(View.GONE);
        }
    }

    String[] messageArray;

    @Override
    public void qd_welcomeCallback(boolean isCache, Response response) {


        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    String result = response.getResult().toString();
                    if (!CommFunAndroid.isNullOrEmpty(result)) {
                        CommFunAndroid.setSharedPreferences("qd_welcomeStr", result);
                        setTextSwitch(result);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextSwitch(String result) {
        if (!CommFunAndroid.isNullOrEmpty(result)) {
            messageArray = result.split(",");

            TextSwitchView tv_message = mHolder.getView(R.id.tv_message);
            // showMsg(result + (tv_message == null));

            tv_message.setResources(messageArray);
            tv_message.setTextStillTime(5000);
        }
    }


    /**
     * 头像点击弹出抽屉菜单
     *
     * @param view
     */
    @Event(value = R.id.img_head)
    private void img_headClick(View view) {
        // Tab1MenuPopupWindow popupWindow = new
        // Tab1MenuPopupWindow(getActivity());
        // popupWindow.showPopupWindow(mContentView);
        ((MainActivity) getActivity()).toggleMenu();
    }


    /**
     * 跳转到商家列表
     *
     * @param view
     */
    @Event(value = R.id.ll_search)
    private void ll_searchClick(View view) {
        startActivity(new Intent(mContext, StoreInfoListActivity.class));
    }

    /**
     * 装潢改装
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        Intent intent = new Intent(mContext, StoreInfoListActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent);
    }

    /**
     * 机油辅料
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {

        Intent intent = new Intent(mContext, StoreInfoListActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent);

    }

    /**
     * 汽保工具
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        Intent intent = new Intent(mContext, StoreInfoListActivity.class);
        intent.putExtra("flag", 3);
        startActivity(intent);
    }

    /**
     * 汽修厂家
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {

        startActivity(new Intent(mContext, CityListActivity.class));
    }

    /**
     * 维修配件
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {

        startActivity(new Intent(mContext, AskPriceActivity.class));
        //Intent intent = new Intent(mContext, MyMsgActivity.class);

        // startActivity(intent);
    }

    /**
     * 促销商家
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {
        Intent intent = new Intent(mContext, StoreInfoListActivity.class);
        intent.putExtra("flag", 4);
        startActivity(intent);
    }

    /**
     * 小车配件
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_7)
    private void ll_menClick(View view) {
        Intent intent = new Intent(mContext, CarTypeActivity.class);

        startActivity(intent);
    }

    /**
     * 交易担保
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_8)
    private void ll_menulick(View view) {
        Intent intent = new Intent(mContext, TransactionActivity.class);

        startActivity(intent);
    }

    /**
     * 积分签到
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_9)
    private void ll_menu_9Click(View view) {
        if (isLogin())
            mPresenter.store_sign();
    }

    @Override
    public void store_signCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    int score = response.getNumber();
                    showSignDialog(score);
                } else {
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSignDialog(int score) {
        SignSuccessDialog dialog = new SignSuccessDialog(getActivity(), score);
        dialog.setDialogListener(new SignSuccessDialog.DialogListener() {

            @Override
            public void onOkClick() {
                //showMsg("ok");
            }

            @Override
            public void onCancelClick() {
                //showMsg("cancel");
            }
        });
        dialog.show();
    }


}
