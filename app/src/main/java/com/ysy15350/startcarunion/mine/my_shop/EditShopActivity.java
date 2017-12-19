package com.ysy15350.startcarunion.mine.my_shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.jph.takephoto.model.TImage;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.GridViewAdpater_Atlas;
import com.ysy15350.startcarunion.image_select.ImgSelectActivity;
import com.ysy15350.startcarunion.store.CityListActivity;
import com.ysy15350.startcarunion.store_select.StoreSelectListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import api.model.CarType;
import api.model.StoreInfo;
import base.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.JsonConvertor;
import custom_view.dialog.ConfirmDialog;
import custom_view.popupwindow.PhotoSelectPopupWindow;


@ContentView(R.layout.activity_edit_shop)
public class EditShopActivity extends MVPBaseActivity<EditShopViewInterface, EditShopPresenter>
        implements EditShopViewInterface {

    private static final String TAG = "EditShopActivity";

    @ViewInject(R.id.et_tent)
    private EditText et_tent;

    @ViewInject(R.id.img_ad)
    private ImageView img_ad;

    @ViewInject(R.id.gv_atlas)
    private GridView gv_atlas;

    /**
     * 店招图片ID,多张以”,”分隔
     */
    private String mAd_img = "";

    /**
     * 品牌ID,多个“@”分隔
     */
    private String mBrand = "";

    /**
     * 品牌名用，隔开
     */
    private String mBrand_names = "";


    private int city_id = -1;
    private String city_name = "";


    @Override
    protected EditShopPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new EditShopPresenter(EditShopActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (null != savedInstanceState) {
//            mStoreInfo = (StoreInfo) savedInstanceState.getSerializable("mStoreInfo");
//            bindStoreInfo(mStoreInfo);
//            mAd_img = savedInstanceState.getString("mAd_img");
//            mBrand = savedInstanceState.getString("mBrand");
//            mAtlas = savedInstanceState.getString("mAtlas");
//            city_name = savedInstanceState.getString("city_name");
//            city_id = savedInstanceState.getInt("city_id");
//            //showMsg("读取状态");
//            if (CommFunAndroid.isNullOrEmpty(BaseData.getCache("istemp")))
//                mPresenter.store_info();
//        }

        String mStoreInfoJson = BaseData.getCache("mStoreInfo");
        if (!CommFunAndroid.isNullOrEmpty(mStoreInfo))
            mStoreInfo = JsonConvertor.fromJson(mStoreInfoJson, StoreInfo.class);

        if (mStoreInfo != null) {
            bindStoreInfo(mStoreInfo);
        }

        String isTemp = BaseData.getCache("istemp");
        if (CommFunAndroid.isNullOrEmpty(isTemp) || mStoreInfo == null) {
            showWaitDialog("数据加载中...");
            mPresenter.store_info();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();

        imageList.add(img_ad);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setFormHead("店铺管理");


        et_tent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                // mHolder.setText(R.id.et_tent_count, start + "");
                // showMsg(s.toString() + start + before);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                mHolder.setText(R.id.tv_tent_count, s.length() + "");
            }
        });


        gv_atlas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                CarType carType = (CarType) adapterView.getItemAtPosition(i);
                if (CommFun.isNullOrEmpty(carType.getTitle())) {
                    img_upload(2, 300, 300);
                } else {
                    ConfirmDialog dialog = new ConfirmDialog(EditShopActivity.this, "是否删除？");
                    dialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {
                            CarType carType = (CarType) adapterView.getItemAtPosition(i);
                            if (carType != null)
                                delAtlas(carType.getId());

                        }
                    });
                    dialog.show();
                }
            }
        });

        gv_atlas.setOnLongClickListener(new View.OnLongClickListener()

        {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

    }


    private final static int BRAND = 101;
    private final static int CITY = 102;
    private final static int TELL = 103;
    private final static int MOBILE = 104;

    /**
     * 品牌选择
     *
     * @param view
     */
    @Event(value = R.id.ll_store)
    private void ll_storeClick(View view) {
        saveCache();
        Intent intent = new Intent(this, StoreSelectListActivity.class);
        startActivityForResult(intent, BRAND);
    }


    /**
     * 城市选择
     *
     * @param view
     */
    @Event(value = R.id.ll_city)
    private void ll_cityClick(View view) {
        saveCache();
        Intent intent = new Intent(this, CityListActivity.class);
        CityListActivity.type = 1;
        startActivityForResult(intent, CITY);
    }


    /**
     * 选择电话，可选多个
     *
     * @param view
     */
    @Event(value = R.id.tv_tell)
    private void tv_tellClick(View view) {
        saveCache();
        Intent intent = new Intent(this, TellListActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, TELL);
    }

    /**
     * 选择电话，可选多个
     *
     * @param view
     */
    @Event(value = R.id.tv_mobile)
    private void tv_mobileClick(View view) {
        saveCache();
        Intent intent = new Intent(this, TellListActivity.class);
        intent.putExtra("type", 2);
        startActivityForResult(intent, MOBILE);
    }

    private StoreInfo mStoreInfo;

    @Override
    public void bindData() {
        super.bindData();

        bindStoreInfo(mStoreInfo);
    }

    List<ImageView> imageList = new ArrayList<>();

    /**
     * 绑定详情信息
     */
    private void bindStoreInfo(StoreInfo storeInfo) {

        try {
            if (storeInfo != null) {
                city_id = storeInfo.getCity_id();
                city_name = storeInfo.getCity_name();

                String ad_img_litpic = "";

                List<String> ad_img_litpicList = storeInfo.getAd_img_litpic();

                if (ad_img_litpicList != null && !ad_img_litpicList.isEmpty()) {
                    //showMsg(ad_img_litpicList.get(0));
                    ad_img_litpic = Config.getServer() + ad_img_litpicList.get(0);
                }

                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getPid_title()))
                    mHolder.setText(R.id.tv_brand, storeInfo.getPid_title());
                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getCity_name()))
                    mHolder.setText(R.id.tv_city, storeInfo.getCity_name());
                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getTell())) {
                    String tell = storeInfo.getTell();


                    mHolder.setText(R.id.tv_tell, getTellOrMobile(tell));
                }

                if (!CommFunAndroid.isNullOrEmpty(storeInfo.getMobile_list())) {
                    String tell = storeInfo.getMobile_list();


                    mHolder.setText(R.id.tv_mobile, getTellOrMobile(tell));
                }

                //----------图册-----------
                String atlas = storeInfo.getAtlas();
                setAtlasList(atlas, storeInfo.getAtlas_litpic());//拼装图册
                bindAtlasGrid();//绑定图册


                //----------图册end-----------

                mHolder
                        .setImageURL(R.id.img_ad, ad_img_litpic, 480, 290)
                        .setText(R.id.tv_userName, storeInfo.getFullname())
                        .setText(R.id.et_tent, storeInfo.getContent())
                        .setText(R.id.et_address, storeInfo.getAddress())
                        .setText(R.id.et_qq, storeInfo.getQq())
                        .setText(R.id.et_email, storeInfo.getEmail())
                        .setText(R.id.et_type_info, storeInfo.getType_info())
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼装图册
     *
     * @param idsStr
     * @param urls
     */
    private List<CarType> setAtlasList(String idsStr, List<String> urls) {

        List<CarType> carTypeListOld = getAtlasList();

        List<CarType> carTypeList = new ArrayList<>();

        try {

            if (!CommFun.isNullOrEmpty(idsStr) && urls != null) {

                int index = idsStr.indexOf(",");
                if (idsStr.indexOf(",") == 0) {
                    idsStr = idsStr.substring(1, idsStr.length());
                }
                String[] atlasArr = idsStr.split(",");


                if (atlasArr != null && atlasArr.length > 0) {
                    for (int i = 0; i < atlasArr.length; i++) {

                        if (urls != null && urls.size() > i) {
                            String atlasItem = atlasArr[i];
                            CarType carType = new CarType();
                            carType.setId(CommFun.toInt32(atlasItem, 0));
                            carType.setTitle(atlasItem);
                            carType.setIcon(urls.get(i));
                            carTypeList.add(carType);
                        }
                    }
                }

                if (carTypeList != null && !carTypeList.isEmpty()) {
                    String atlasListJson = JsonConvertor.toJson(carTypeList);
                    BaseData.setCache("atlasListJson", atlasListJson);
                } else {
                    BaseData.setCache("atlasListJson", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return carTypeList;
    }

    /**
     * 获取图册列表
     *
     * @return
     */
    public List<CarType> getAtlasList() {
        try {
            String atlasListJson = BaseData.getCache("atlasListJson");
            if (!CommFun.isNullOrEmpty(atlasListJson)) {
                List<CarType> carTypeList = JsonConvertor.fromJson(atlasListJson, new TypeToken<List<CarType>>() {
                }.getType());
                return carTypeList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加图册
     *
     * @param id
     * @param url
     */
    public void addAtlas(int id, String url) {
        try {
            List<CarType> carTypeList = getAtlasList();

            if (carTypeList == null)
                carTypeList = new ArrayList<>();

            CarType carType = new CarType();
            carType.setId(id);
            carType.setTitle(id + "");
            carType.setIcon(url);

            carTypeList.add(carType);

            if (carTypeList != null && !carTypeList.isEmpty()) {
                String atlasListJson = JsonConvertor.toJson(carTypeList);
                BaseData.setCache("atlasListJson", atlasListJson);
            }
            bindAtlasGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delAtlas(int id) {
        try {
            if (id != 0) {
                List<CarType> carTypeList = getAtlasList();
                List<CarType> carTypeListNew = new ArrayList<>();
                if (carTypeList != null) {

                    for (CarType carType :
                            carTypeList) {
                        if (carType != null) {
                            if (carType.getId() != id) {
                                carTypeListNew.add(carType);
                            }
                        }
                    }

                    String atlasListJson = JsonConvertor.toJson(carTypeListNew);
                    BaseData.setCache("atlasListJson", atlasListJson);

                    bindAtlasGrid();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAtlas() {
        String atlas = "";

        try {
            List<CarType> carTypeList = getAtlasList();
            if (carTypeList != null) {
                for (CarType carType :
                        carTypeList) {
                    if (carType != null) {
                        atlas = atlas + carType.getTitle() + ",";
                    }
                }
            }
            if (!CommFun.isNullOrEmpty(atlas)) {
                atlas = atlas.substring(0, atlas.length() - 1);
                return atlas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> getAtlasUrls() {
        List<String> atlas = new ArrayList<>();

        try {
            List<CarType> carTypeList = getAtlasList();
            if (carTypeList != null) {
                for (CarType carType :
                        carTypeList) {
                    if (carType != null) {
                        atlas.add(carType.getIcon());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return atlas;
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


    GridViewAdpater_Atlas mAdapter;

    private void bindAtlasGrid() {

        try {
            List<CarType> carTypeList = getAtlasList();
            if (carTypeList == null)
                carTypeList = new ArrayList<>();
            carTypeList.add(new CarType());
            mAdapter = new GridViewAdpater_Atlas(this, carTypeList);
            gv_atlas.setAdapter(mAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void store_infoCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    String result = response.getResultJson();
                    BaseData.setCache("mStoreInfo", result);
                    Log.i(TAG, result);

                    mStoreInfo = response.getData(StoreInfo.class);
                    if (mStoreInfo != null) {
                        mBrand_names = mStoreInfo.getPid_title();
                        mBrand = mStoreInfo.getPid();
                    }
                    bindStoreInfo(mStoreInfo);
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public void save_store_infoCallback(Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("istemp", "");
                    checkAppStatus();//如果是重新打开的应用，重新从入口进入，缺陷：不会回到选择照片的页面
                    finish();
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


            if (CommFunAndroid.isNullOrEmpty(mAd_img)) {
                if (mStoreInfo != null)
                    mAd_img = mStoreInfo.getAd_img();
            }
            // showMsg("店招图片" + mAd_img);
            String ad_img = mStoreInfo.getAd_img();//店招图片ID,多张以”,”分隔
            String tent = mHolder.getViewText(R.id.et_tent);//店铺简介
            String tell = mHolder.getViewText(R.id.tv_tell);//联系方式
            String address = mHolder.getViewText(R.id.et_address);//地址
            String qq = mHolder.getViewText(R.id.et_qq);//qq
            String email = mHolder.getViewText(R.id.et_email);
            String brand = mStoreInfo.getPid();//品牌ID,多个“@”分隔
            String type_info = mHolder.getViewText(R.id.et_type_info);


            //showMsg(mAtlas);
            String atlas = getAtlas();//店铺图集ID,多张以”,”分隔

            mPresenter.save_store_info(ad_img, tent, tell, city_id, address, qq, email, brand, atlas, type_info);
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }
    }


    //------------------选择头像、或者拍摄-----------------------


    @Event(value = R.id.img_upload_ad)
    private void img_upload_adClick(View view) {
        img_upload(1, 480, 290);
    }

    @Event(value = R.id.ll_upload_atlas)
    private void ll_upload_atlasClick(View view) {
        img_upload(2, 300, 300);
        //add_atlas();
    }

    private void img_upload(int type, int width, int height) {

        saveCache();

        BaseData.setCache("img_upload_type", type + "");
        BaseData.setCache("cropWidth", width + "");
        BaseData.setCache("cropHeigth", height + "");


        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PhotoSelectPopupWindow.PopListener() {
            @Override
            public void onTakePhotoClick() {
                getPhoto(2);
            }

            @Override
            public void onSelectPhotoClick() {
                //gallery();
                getPhoto(1);
            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    private void getPhoto(int type) {


        Intent intent = new Intent(this, ImgSelectActivity.class);
        CommFunAndroid.setSharedPreferences("type", type + "");
        String img_upload_type = BaseData.getCache("img_upload_type");
        if ("1".equals(img_upload_type)) {
            intent.putExtra("width", 480);
            intent.putExtra("height", 290);
        } else if ("2".equals(img_upload_type)) {
            intent.putExtra("width", 300);
            intent.putExtra("height", 300);
        }

        startActivityForResult(intent, PHOTO_REQUEST);
    }

    private static final int PHOTO_REQUEST = 100;// 选择照片
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private Bitmap bitmap;

    private String headImage;

    /*
     * 从相机获取
	 */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (CommFunAndroid.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(CommFunAndroid.getDiskCachePath(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /*
     * 从相册获取
	 */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private File tempSmallFile;

    String tempFilePath;

    Uri uritempFile;


    /**
     * 剪切图片
     *
     * @param uri
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     */
    private void crop(Uri uri) {

        int cropWidth = CommFunAndroid.toInt32(BaseData.getCache("cropWidth"), 300);
        int cropHeigth = CommFunAndroid.toInt32(BaseData.getCache("cropHeigth"), 300);


        Log.i(TAG, this.toString() + cropWidth + "," + cropHeigth);

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", cropWidth);
        intent.putExtra("aspectY", cropHeigth);
        //showMsg(cropWidth + "," + cropHeigth);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", CommFunAndroid.dip2px(cropWidth));
        intent.putExtra("outputY", CommFunAndroid.dip2px(cropHeigth));
        // 图片格式
        //intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        //intent.putExtra("return-data", true);// true:不返回uri，false：返回uri//此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）

        //uritempFile为Uri类变量，实例化uritempFile
        tempFilePath = "file://" + "/" + CommFunAndroid.getDiskCachePath() + "/" + "small.jpg";

        uritempFile = Uri.parse(tempFilePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public static int mRequestCode;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            //showMsg(requestCode + "," + resultCode);
            mRequestCode = requestCode;
            if (requestCode == PHOTO_REQUEST) {
                if (resultCode == RESULT_OK) {
                    ArrayList<TImage> images = (ArrayList<TImage>) data.getSerializableExtra("images");
                    if (images != null && !images.isEmpty()) {
                        String path = images.get(0).getOriginalPath();
                        if (CommFunAndroid.isNullOrEmpty(path))
                            path = images.get(0).getCompressPath();

                        File file = new File(path);
//                        if (file != null && file.exists()) {
//                            this.img_ad.setImageURI(Uri.parse(path));
//                        }
                        uploadImage(file);
                    }
                }
            } else if (requestCode == PHOTO_REQUEST_GALLERY) {
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri);
                }
            } else if (requestCode == PHOTO_REQUEST_CAMERA) {
                if (CommFunAndroid.hasSdcard()) {
                    tempFile = new File(CommFunAndroid.getDiskCachePath(), PHOTO_FILE_NAME);
                    crop(Uri.fromFile(tempFile));
                } else {
                    showMsg("未找到存储卡，无法存储照片！");
                }

            } else if (requestCode == PHOTO_REQUEST_CUT) {
                try {
                    //将Uri图片转换为Bitmap
                    InputStream inputStream = getContentResolver().openInputStream(uritempFile);

                    if (inputStream == null) {
                        showMsg("照片丢失");
                        return;
                    }


                    bitmap = BitmapFactory.decodeStream(inputStream);


//                    bitmap = data.getParcelableExtra("data");
                    this.img_ad.setImageBitmap(bitmap);
                    tempSmallFile = new File(uritempFile.getPath());
                    uploadImage(tempSmallFile);//上传头像
                    //boolean delete = tempFile.delete();
                    //Log.i("register", "register tempFile delete = " + delete);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == BRAND) {
                try {
                    String brand = data.getStringExtra("brand");
                    String brand_names = data.getStringExtra("brand_names");
                    mHolder.setText(R.id.tv_brand, mBrand_names);//显示公司经营品牌（中文）
                    mBrand = brand;
                    mStoreInfo.setPid(brand);
                    mStoreInfo.setPid_title(brand_names);

                    //showMsg(brand_names + brand);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CITY) {
                try {

                    city_id = data.getIntExtra("city_id", -1);
                    city_name = data.getStringExtra("city_name");
                    mHolder.setText(R.id.tv_city, city_name);//显示公司经营品牌（中文）
                    mStoreInfo.setCity_id(city_id);
                    mStoreInfo.setCity_name(city_name);

                    //showMsg(city_id + city_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == TELL) {
                try {
                    String phoneStr = data.getStringExtra("phoneStr");
                    if (!CommFunAndroid.isNullOrEmpty(phoneStr)) {
                        mStoreInfo.setTell(phoneStr);

                    }
                    if (CommFunAndroid.isNullOrEmpty(phoneStr))
                        phoneStr = "点击添加";
                    mHolder.setText(R.id.tv_tell, phoneStr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == MOBILE) {
                try {
                    String phoneStr = data.getStringExtra("phoneStr");
                    if (!CommFunAndroid.isNullOrEmpty(phoneStr)) {
                        mStoreInfo.setTell(phoneStr);

                    }
                    if (CommFunAndroid.isNullOrEmpty(phoneStr))
                        phoneStr = "点击添加";
                    mHolder.setText(R.id.tv_mobile, phoneStr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }
        saveCache();
    }

    private void uploadImage(File file) {

        if (file != null && file.exists()) {
            showWaitDialog("图片上传中...");
            mPresenter.uppicture(file);
        } else {
            showMsg("文件丢失");
        }

    }

    @Override
    public void uppictureCallback(Response response) {
        try {
            if (response != null) {
                //String msg = response.getMessage();
                if (response.getCode() == 200) {
                    //showMsg(response.getId() + "上传成功" + response.getPic());
                    showMsg("图片上传成功");
                    int imgId = response.getId();
                    String imgUrl = response.getPic();

                    String img_upload_type = BaseData.getCache("img_upload_type");
                    if ("1".equals(img_upload_type)) {
                        mAd_img = imgId + "";
                        mStoreInfo.setAd_img(mAd_img);
                        List<String> adImgs = new ArrayList<>();
                        adImgs.add(Config.getServer() + imgUrl);
                        mStoreInfo.setAd_img(mAd_img);
                        mStoreInfo.setAd_img_litpic(adImgs);
                        mHolder.setImageURL(R.id.img_ad, Config.getServer() + imgUrl, 480, 290);
                    } else if ("2".equals(img_upload_type)) {
                        addAtlas(imgId, imgUrl);
                    }

                } else {
                    showMsg("上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveCache() {

        // showMsg("店招图片" + mAd_img);
        String tent = mHolder.getViewText(R.id.et_tent);//店铺简介
        String tell = mHolder.getViewText(R.id.tv_tell);//联系方式
        String address = mHolder.getViewText(R.id.et_address);//地址
        String qq = mHolder.getViewText(R.id.et_qq);//qq
        String email = mHolder.getViewText(R.id.et_email);
        String brand = mBrand;//品牌ID,多个“@”分隔

        BaseData.setCache("istemp", "1");


        if (mStoreInfo != null) {


            mStoreInfo.setContent(tent);
            mStoreInfo.setTell(tell);
            mStoreInfo.setAddress(address);
            mStoreInfo.setQq(qq);
            mStoreInfo.setEmail(email);
            mStoreInfo.setAtlas_litpic(getAtlasUrls());


            BaseData.setCache("mStoreInfo", JsonConvertor.toJson(mStoreInfo));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (imageList != null && !imageList.isEmpty()) {
            for (ImageView image :
                    imageList) {
                //ImageUtil.getInstance().destoryImageView(image);
            }

        }

    }

    @Override
    protected void back() {
        super.back();
        BaseData.setCache("istemp", "");
    }
    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //showMsg("保存状态");
//        outState.putSerializable("mStoreInfo", mStoreInfo);
//        outState.putString("mAd_img", mAd_img);
//        outState.putString("mBrand", mBrand);
//        outState.putString("mAtlas", mAtlas);
//        outState.putString("city_name", city_name);
//        outState.putInt("city_id", city_id);
//    }
}
