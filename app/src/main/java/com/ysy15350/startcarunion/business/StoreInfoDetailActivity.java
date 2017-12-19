package com.ysy15350.startcarunion.business;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import api.model.StoreInfo;
import base.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.JsonConvertor;
import custom_view.popupwindow.CallPopupWindow;


@ContentView(R.layout.activity_store_detail)
public class StoreInfoDetailActivity extends MVPBaseActivity<StoreInfoDetailViewInterface, StoreInfoDetailPresenter>
        implements StoreInfoDetailViewInterface {

    private static final String TAG = "StoreInfoDetailActivity";


    @ViewInject(R.id.img_ad)
    private ImageView img_ad;


    @ViewInject(R.id.ll_img)
    private LinearLayout ll_img;


    @ViewInject(R.id.ll_atlas_litpic)
    private LinearLayout ll_atlas_litpic;


    @Override
    protected StoreInfoDetailPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new StoreInfoDetailPresenter(StoreInfoDetailActivity.this);
    }

    private int mUid;


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mUid = intent.getIntExtra("id", -1);
        String fullname = intent.getStringExtra("fullname");
        setFormHead(fullname);
        if (mUid != -1) {

            String storeInfoJson = BaseData.getCache("storeInfo" + mUid);

            if (!CommFunAndroid.isNullOrEmpty(storeInfoJson)) {
                Log.d(TAG, "onResume() called storeInfoJson=" + storeInfoJson);
                StoreInfo storeInfo = JsonConvertor.fromJson(storeInfoJson, StoreInfo.class);
                bindStoreInfo(storeInfo);
            }

            mPresenter.store_info(mUid);
            mPresenter.store_browse(mUid);
        }

    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    @Override
    public void initData() {
        super.initData();

    }

    private final static int BRAND = 101;


    private StoreInfo mStoreInfo;


    List<ImageView> imageList = new ArrayList<>();

    /**
     * 绑定详情信息
     */
    private void bindStoreInfo(StoreInfo storeInfo) {

        try {
            if (storeInfo != null) {

                String fullName = storeInfo.getFullname();
                setFormHead(fullName);

                String ad_img_litpic = "";

                List<String> ad_img_litpicList = storeInfo.getAd_img_litpic();

                if (ad_img_litpicList != null && !ad_img_litpicList.isEmpty()) {
                    //showMsg(ad_img_litpicList.get(0));
                    ad_img_litpic = Config.getServer() + ad_img_litpicList.get(0);
                }

                List<String> atlas_litpic = storeInfo.getAtlas_litpic();
                if (atlas_litpic != null && !atlas_litpic.isEmpty()) {

                    ll_img.setVisibility(View.VISIBLE);
//                    LinearLayout.LayoutParams ll_atlas_litpic_params = (LinearLayout.LayoutParams) ll_atlas_litpic.getLayoutParams();
//                    ll_atlas_litpic_params.height = ll_atlas_litpic.getChildCount() * 300;
//                    ll_atlas_litpic.setLayoutParams(ll_atlas_litpic_params);

//                    ScreenInfo screenInfo = CommFunAndroid.getInstance(mContext).getScreenInfo();
//
//                    int width = screenInfo.getWidth();
//                    int height = screenInfo.getHeight();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommFunAndroid.dip2px(300));


                    params.setMargins(CommFunAndroid.dip2px(20), CommFunAndroid.dip2px(20), CommFunAndroid.dip2px(20), 0);

                    ll_atlas_litpic.removeAllViews();

                    for (String str :
                            atlas_litpic) {
                        ImageView image = new ImageView(this);

                        image.setLayoutParams(params);

                        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        mHolder.setImageURL(image, Config.getServer() + str, 300, 300);

                        ll_atlas_litpic.addView(image);
                        imageList.add(image);
                    }


                    //showMsg(ll_atlas_litpic.getChildCount() + ",");
                    //ListViewAdpater_Atlas adapter = new ListViewAdpater_Atlas(mContext, atlas_litpic);
                    //lv_atlas_litpic.setAdapter(adapter);
                } else {
                    ll_img.setVisibility(View.GONE);
                }


                mHolder
                        .setImageURL(R.id.img_ad, ad_img_litpic, 480, 290)
                        .setText(R.id.tv_fullName, storeInfo.getFullname())
                        .setText(R.id.tv_pid_title, storeInfo.getPid_title())
                        .setText(R.id.tv_tell, storeInfo.getTell())
                        .setText(R.id.tv_mobile, storeInfo.getMobile_list())
                        .setText(R.id.tv_address, storeInfo.getAddress())
                        .setText(R.id.tv_content, storeInfo.getContent())
                        .setText(R.id.tv_qq, storeInfo.getQq())
                        .setText(R.id.tv_email, storeInfo.getEmail())
                        .setText(R.id.tv_type_info, storeInfo.getType_info())
                ;

                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getTell())) {
                    String tell = storeInfo.getTell();


                    mHolder.setText(R.id.tv_tell, getTellOrMobile(tell));
                }

                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getMobile_list())) {
                    String tell = storeInfo.getMobile_list();


                    mHolder.setText(R.id.tv_mobile, getTellOrMobile(tell));
                }

                int coll = storeInfo.getColl();//是否收藏，1:已收藏
                View img_collection = mHolder.getView(R.id.img_collection);

                //showMsg("coll=" + coll);
                img_collection.setEnabled(coll == 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTellOrMobile(String tell) {
        StringBuilder sb = new StringBuilder();
        String[] tells = null;
        if (tell.contains(",")) {
            tells = tell.split(",");
        } else if (tell.contains("/")) {
            tells = tell.split("/");
        }
        if (tells != null && tells.length > 0) {
            for (String str :
                    tells) {
                sb.append(str + "\n");
            }
            sb.delete(sb.length() - 1, sb.length());
        } else {
            sb.append(tell);
        }

        return sb.toString();
    }

    @Override
    public void store_infoCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    String result = response.getResultJson();

                    Log.i(TAG, result);

                    mStoreInfo = response.getData(StoreInfo.class);
                    if (mStoreInfo != null) {
                        BaseData.setCache("storeInfo" + mStoreInfo.getId(), result);
                        bindStoreInfo(mStoreInfo);
                    }
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Event(value = R.id.ll_collection)
    private void ll_collectionClick(View view) {

        if (BaseData.getInstance().getUid() == mUid) {
            showMsg("亲，不能收藏你自己");
            return;
        }
        mPresenter.collection(mUid);
    }

    @Override
    public void collectionCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mPresenter.store_info(mUid);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 评论
     *
     * @param view
     */
    @Event(value = R.id.ll_comment)
    private void ll_commentClick(View view) {

        try {

            if (BaseData.getInstance().getUid() == mUid) {
                showMsg("亲，不能评论你自己");
                return;
            }

            if (mStoreInfo != null) {
                Intent intent = new Intent(this, StoreInfoCommentActivity.class);

                intent.putExtra("id", mStoreInfo.getId());
                //intent.putExtra("pid", mStoreInfo.getPid());

                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 与商家交谈
     *
     * @param view
     */
    @Event(value = R.id.ll_talk)
    private void ll_talkClick(View view) {

        try {
            if (mStoreInfo != null) {
                if (BaseData.getInstance().getUid() == mUid) {
                    showMsg("亲，不能和自己交谈");
                    return;
                }

                int id = mStoreInfo.getId();

                if (id != 0) {

                    int uid = BaseData.getInstance().getUid();

                    if (uid != id) {

                        Intent intent = new Intent(this, TalkActivity.class);


                        intent.putExtra("id", mStoreInfo.getId());
                        //intent.putExtra("pid", mStoreInfo.getPid());

                        startActivity(intent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拨打电话
     *
     * @param view
     */
    @Event(value = R.id.btn_call)
    private void btn_callClick(View view) {


        try {
            if (mStoreInfo != null) {
                final int uid = mStoreInfo.getId();

                if (uid != 0) {


                    List<String> phones = new ArrayList<>();

                    String mobile = mStoreInfo.getMobile();
                    if (!CommFunAndroid.isNullOrEmpty(mobile))
                        phones.add(mobile);

                    String tell = mStoreInfo.getTell();


                    if (!CommFunAndroid.isNullOrEmpty(tell)) {

                        String[] phonesArr = tell.split("/");

                        if (phonesArr != null && phonesArr.length > 0) {
                            for (String phone :
                                    phonesArr) {
                                phones.add(phone);
                            }
                        }
                    }

                    if (phones == null || phones.size() == 0) {
                        return;
                    }

                    CallPopupWindow popupWindow = new CallPopupWindow(StoreInfoDetailActivity.this, phones);

                    popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                        @Override
                        public void onCallMobile(String mobile) {
                            mPresenter.make_tell(uid, mobile);
                            //showMsg(mobile);
                            callPhoneCheckPermission(StoreInfoDetailActivity.this, mobile);
                        }


                        @Override
                        public void onCancelClick() {

                        }
                    });
                    popupWindow.showPopupWindow(mContentView);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void make_tellCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                }
                //showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store_browseCallback(boolean isCache, Response response) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ImageUtil.getInstance(mContext).destoryImageView(img_ad);
        if (imageList != null && !imageList.isEmpty()) {
            for (ImageView imgae :
                    imageList) {
                //ImageUtil.getInstance(mContext).destoryImageView(imgae);
            }

        }
    }
}
