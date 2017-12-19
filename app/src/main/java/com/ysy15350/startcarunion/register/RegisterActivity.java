package com.ysy15350.startcarunion.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jph.takephoto.model.TImage;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.lljjcoder.citylist.CityListSelectActivity;
import com.lljjcoder.citylist.bean.CityInfoBean;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.startcarunion.MainActivity;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.image_select.ImgSelectActivity;
import com.ysy15350.startcarunion.login.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import api.MemberApi;
import api.base.model.Config;
import api.base.model.Response;
import api.base.server.ApiCallBack;
import api.impl.MemberApiImpl;
import base.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.CommFunMessage;
import custom_view.popupwindow.PhotoSelectPopupWindow;


@ContentView(R.layout.activity_register)
public class RegisterActivity extends MVPBaseActivity<RegisterViewInterface, RegisterPresenter>
        implements RegisterViewInterface, Validator.ValidationListener {


    @Override
    protected RegisterPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new RegisterPresenter(RegisterActivity.this);
    }

//Validator表单验证：    http://blog.csdn.net/yechaoa/article/details/60875310

    //github:https://github.com/ragunathjawahar/android-saripaar

    /**
     * 表单验证器
     */
    Validator validator;

    /**
     * 图片ID,头像上传成功返回
     */
    private int icon;

    /**
     * 类型1:商家  2:经销商
     */
    private int type;


    @ViewInject(R.id.img_head)
    private ImageView img_head;

    /**
     * 公司名称
     */
    @ViewInject(R.id.et_fullname)
    @NotEmpty(messageResId = R.string.register_hint_2)
    @Order(2)
    private EditText et_fullname;


//    /**
//     * 详细地址
//     */
//    @ViewInject(R.id.et_address)
//    @NotEmpty(messageResId = R.string.register_hint_3)
//    @Order(3)
//    private EditText et_address;


    /**
     * 手机号
     */
    @ViewInject(R.id.et_mobile)
    @Pattern(regex = "^0?(13[0-9]|15[012356789]|17[03479]|18[01236789]|14[57])[0-9]{8}$", messageResId = R.string.register_phone_error)
    @Order(5)
    private EditText et_mobile;

    /**
     * 验证码
     */
    @ViewInject(R.id.et_verification_code)
    @NotEmpty(messageResId = R.string.register_hint_6)
    @Order(6)
    private EditText et_verification_code;


    /**
     * 密码
     */
    @ViewInject(R.id.et_password)
    @Password(messageResId = R.string.register_password_error)
//,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    @Order(7)
    private EditText et_password;

    /**
     * 重复密码
     */
    @ViewInject(R.id.et_password1)
    @ConfirmPassword(messageResId = R.string.register_password_different)
    @Order(8)
    private EditText et_password1;


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("注册");

        validator = new Validator(this);

        validator.setValidationListener(this);
    }

    @Event(value = R.id.img_head)
    private void img_headClick(View view) {
        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(et_password1);

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


    @Event(value = R.id.tv_type)
    private void tv_typeClick(View view) {
        final CharSequence[] charSequences = {"汽配商", "修理厂"};
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme));

        builder.setTitle("选择类型")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(charSequences, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showMsg(charSequences[which].toString());
                        mHolder.setText(R.id.tv_type, charSequences[which]);
                        type = which + 1;
                    }
                }).show();
    }


    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        //getCityActivity();
    }


    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        getCity();
    }


    @Event(value = R.id.et_address)
    private void et_addressClick(View view) {
        getCity();
    }


    private void getCityActivity() {
//        https://github.com/crazyandcoder/citypicker
        Intent intent = new Intent(this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
    }

    private void getCity() {
        CommFunAndroid.hideSoftInput(mHolder.getView(R.id.ll_menu_3));
        //https://github.com/crazyandcoder/citypicker
        CityPickerView cityPicker = new CityPickerView.Builder(RegisterActivity.this)
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#2091cb")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .province("四川省")
                .city("成都市")
                //.district("锦江区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                StringBuilder sb = new StringBuilder();
                if (province != null)
                    sb.append(province.getName());
                if (city != null)
                    sb.append(city.getName() + "市");
                if (district != null)
                    sb.append(district.getName());
                mHolder.setText(R.id.et_address, sb.toString().trim());
            }

            @Override
            public void onCancel() {

            }
        });
    }


    /**
     * 注册
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        validator.validate();

        if (validationSucceeded) {
            String fullname = mHolder.getViewText(R.id.et_fullname);//公司名称
            String address = mHolder.getViewText(R.id.et_address);//详细地址
            String mobile = mHolder.getViewText(R.id.et_mobile);//手机号
            String code = mHolder.getViewText(R.id.et_verification_code);//验证码
            String password = mHolder.getViewText(R.id.et_password);//密码

            //showMsg(icon + "," + type);
            if (type == 0) {
                showMsg("请选择类型");
                return;
            }

            String id = BaseData.getCache("icon");
            if (!CommFunAndroid.isNullOrEmpty(id))
                icon = CommFunAndroid.toInt32(id, 0);

            mPresenter.register(icon, mobile, fullname, password, address, type, code);
        }


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

        this.finish();
    }

    @Override
    public void registerCallback(Response response) {

        try {
            if (response != null) {
                String msg = response.getMessage();
                if (response.getCode() == 200) {
                    String mobile = mHolder.getViewText(R.id.et_mobile);//手机号
                    String password = mHolder.getViewText(R.id.et_password);//密码
//                    showMsg("注册成功，请登录！");
//                    this.finish();
                    //startActivity(new Intent(mContext, LoginActivity.class));
                    mPresenter.login(mobile, password);
                } else {
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void userLoginCallBack(Response response) {
        // TODO Auto-generated method stub

        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {
                startActivity(new Intent(this, MainActivity.class));
                BaseData.getInstance().setUid(response.getUid());

            } else {
                startActivity(new Intent(this, LoginActivity.class));
                showMsg("登录失败," + msg);
            }
            this.finish();
            //showMsg(msg);
        }
    }

    @Override
    public void uppictureCallback(Response response) {
        try {
            if (response != null) {
                //String msg = response.getMessage();
                if (response.getCode() == 200) {
                    showMsg("上传成功");
                    int id = response.getId();

                    BaseData.setCache("icon", id + "");

                    String imgUrl = Config.getServer() + response.getPic();

                    mHolder.setImageURL(R.id.img_head, imgUrl, true);
                } else {
                    BaseData.setCache("icon", "");
                    showMsg("上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }

    //------------------选择头像、或者拍摄-----------------------
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

                    if (inputStream == null) {
                        showMsg("照片丢失");
                        return;
                    }


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
                    showMsg(e.getMessage());
                }
            } else if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    Bundle bundle = data.getExtras();

                    CityInfoBean cityInfoBean = (CityInfoBean) bundle.getParcelable("cityinfo");

                    if (null == cityInfoBean)
                        return;

                    //城市名称
                    String cityName = cityInfoBean.getName();
                    //纬度
                    String latitude = cityInfoBean.getLongitude();
                    //经度
                    String longitude = cityInfoBean.getLatitude();

                    //获取到城市名称，经纬度值后可自行使用...
                    showMsg(cityName + "," + cityInfoBean.getFistLetter());

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
            showMsg("文件丢失");
        }

    }

    Timer timer;
    TimerTask task;

    @ViewInject(R.id.btn_send_code)
    private Button btn_send_code;

    @Event(value = R.id.btn_send_code)
    private void btn_get_codeClick(View view) {
        send_mobile_code();

    }

    MemberApi memberApi = new MemberApiImpl();

    /**
     * 发送验证码
     */
    private void send_mobile_code() {
        String phone = mHolder.getViewText(R.id.et_mobile);


        if (CommFunAndroid.isNullOrEmpty(phone)) {
            showMsg("请输入手机号");
            return;
        }

        showWaitDialog("短信发送中...");

        memberApi.send_mobile_code("", phone, "register", new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                CommFunMessage.hideWaitDialog();
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {
                        codeTimer();// 启动定时器
                    }
                    showMsg(msg);
                }
            }
        });
    }

    final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            mHolder.setText(R.id.btn_send_code, "获取验证码(" + time_second_temp + ")");

            if (time_second_temp == 0) {
                time_second_temp = time_second;
                btn_send_code.setEnabled(true);
                mHolder.setText(R.id.btn_send_code, "获取验证码");
            }

            return false;
        }
    });


    int time_second = 60;

    private static int time_second_temp = 0;

    /**
     * 定时器
     */
    private void codeTimer() {
        try {
            if (time_second_temp == 0) {
                time_second_temp = time_second;
                if (timer != null)
                    timer.cancel();
                if (task != null)
                    task.cancel();
            }

            btn_send_code.setEnabled(false);

            timer = new Timer();

            long delay = 0;
            long intevalPeriod = 1 * 1000;
            // schedules the task to be run in an interval

            task = new TimerTask() {
                @Override
                public void run() {

                    time_second_temp--;

                    if (time_second_temp == 0) {
                        timer.cancel();
                    }

                    handler.sendEmptyMessage(0);
                }
            };

            timer.scheduleAtFixedRate(task, delay, intevalPeriod);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
