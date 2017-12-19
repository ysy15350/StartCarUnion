package com.ysy15350.startcarunion.mine.my_info;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.jph.takephoto.model.TImage;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.databinding.ActivityMyInfoBinding;
import com.ysy15350.startcarunion.forgot_pwd.ForgotPwdActivity;
import com.ysy15350.startcarunion.image_select.ImgSelectActivity;
import com.ysy15350.startcarunion.mine.UpdateCompanyNameActivity;
import com.ysy15350.startcarunion.mine.UpdateNicknameActivity;
import com.ysy15350.startcarunion.mine.UpdatePhoneActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import api.base.model.Config;
import api.base.model.Response;
import base.BaseData;
import base.config.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import custom_view.popupwindow.PhotoSelectPopupWindow;

import static common.CommFunAndroid.hasSdcard;

/**
 * 设置
 *
 * @author yangshiyou
 */
//@ContentView(R.layout.activity_my_info)
public class MyInfoActivity extends MVPBaseActivity<MyInfoViewInterface, MyInfoPresenter>
        implements MyInfoViewInterface {


    @Override
    protected MyInfoPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new MyInfoPresenter(MyInfoActivity.this);
    }

//    DataBinding
//
//    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0603/2992.html
//
//    http://www.jianshu.com/p/9c99a4bf7c9d
//
//    http://www.jianshu.com/p/ba4982be30f8


    ActivityMyInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showWaitDialog("数据加载中...");
        mPresenter.user_info();
    }

    @Override
    public void initView() {

        super.initView();

        setFormHead("个人资料");
    }

    private UserInfo mUserInfo;


    @Override
    public void bindData() {
        super.bindData();
        mUserInfo = BaseData.getInstance().getUserInfo();
        if (mUserInfo != null) {
            if (!CommFunAndroid.isNullOrEmpty(mUserInfo.getLitpic())) {

                mHolder.setImageURL(R.id.img_head,
                        Config.getServer() + mUserInfo.getLitpic(),
                        true);
            }
            binding.setUser(mUserInfo);
        }
    }

    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    mUserInfo = response.getData(UserInfo.class);
                    BaseData.getInstance().setUserInfo(mUserInfo);

                    bindData();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save_infoCallback(Response response) {
        try {
            if (response != null) {
                String msg = response.getMessage();
                if (response.getCode() == 200) {
                    //checkAppStatus();//如果是重新打开的应用，重新从入口进入，缺陷：不会回到选择照片的页面
                    //finish();
                }
                mPresenter.user_info();
                showMsg(msg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uppictureCallback(Response response) {
        try {
            if (response != null) {
                //String msg = response.getMessage();
                if (response.getCode() == 200) {
                    showMsg("上传成功");
                    String imgUrl = Config.getServer() + response.getPic();

                    mHolder.setImageURL(R.id.img_head, imgUrl, true);

                    mPresenter.save_info("icon", response.getId() + "");

                } else {
                    showMsg("上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改公司名称
     *
     * @param view
     */
    @Event(value = R.id.ll_company_name)
    private void ll_company_nameClick(View view) {
        startActivity(new Intent(this, UpdateCompanyNameActivity.class));
    }


    /**
     * 修改昵称
     *
     * @param view
     */
    @Event(value = R.id.ll_nickname)
    private void ll_nicknameClick(View view) {
        startActivity(new Intent(this, UpdateNicknameActivity.class));
    }


    /**
     * 修改手机号
     *
     * @param view
     */
    @Event(value = R.id.ll_phone)
    private void ll_phoneClick(View view) {
        Intent intent = new Intent(this, UpdatePhoneActivity.class);
        intent.putExtra("phone", mUserInfo.getMobile());
        startActivity(intent);
    }


    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_pwd)
    private void ll_pwdClick(View view) {
        Intent intent = new Intent(this, ForgotPwdActivity.class);
        intent.putExtra("type", 1);
        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        if (userInfo != null) {
            String mobile = userInfo.getMobile();
            intent.putExtra("phone", mobile);
        }
        startActivity(intent);
    }


    //------------------选择头像、或者拍摄-----------------------

    @ViewInject(R.id.img_head)
    private ImageView img_head;

    @Event(value = R.id.img_head)
    private void img_headClick(View view) {
        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PhotoSelectPopupWindow.PopListener() {
            @Override
            public void onTakePhotoClick() {
                getPhoto(2);
            }

            @Override
            public void onSelectPhotoClick() {
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
        intent.putExtra("width", 300);
        intent.putExtra("height", 300);
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
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
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
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", CommFunAndroid.dip2px(250));
        intent.putExtra("outputY", CommFunAndroid.dip2px(250));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //showMsg(requestCode + "," + resultCode);
        try {
            if (requestCode == PHOTO_REQUEST) {
                if (resultCode == RESULT_OK) {
                    ArrayList<TImage> images = (ArrayList<TImage>) data.getSerializableExtra("images");
                    if (images != null && !images.isEmpty()) {
                        String path = images.get(0).getOriginalPath();

                        File file = new File(path);
                        if (file != null && file.exists()) {
                            this.img_head.setImageURI(Uri.parse(path));
                        }
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


                    bitmap = BitmapFactory.decodeStream(inputStream);


//                    bitmap = data.getParcelableExtra("data");
                    this.img_head.setImageBitmap(bitmap);
                    //showMsg(uritempFile.getPath());
                    tempSmallFile = new File(uritempFile.getPath());
                    uploadImage(tempSmallFile);//上传头像
                    //boolean delete = tempFile.delete();
                    //Log.i("register", "register tempFile delete = " + delete);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(File file) {
        if (file != null && file.exists()) {
            mPresenter.uppicture(file);
        } else {
            showMsg("文件不存在");
        }

    }


}
