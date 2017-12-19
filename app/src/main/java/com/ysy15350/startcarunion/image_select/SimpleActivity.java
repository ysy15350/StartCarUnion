package com.ysy15350.startcarunion.image_select;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.ysy15350.startcarunion.R;

import java.io.File;
import java.util.ArrayList;

import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/12.
 */

public class SimpleActivity extends TakePhotoActivity {

    private ImageView img_head;

    TakePhoto takePhoto;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        img_head = this.findViewById(R.id.img_head);
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                File file = new File(CommFunAndroid.getDiskCachePath(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();

                takePhoto = getTakePhoto();

                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);

                imageUri = Uri.fromFile(file);
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions(300, 300));
            }
        });

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        if (images != null && !images.isEmpty()) {
            String path = images.get(0).getOriginalPath();
            showImage(path);
        }
    }

    private void showImage(String path) {
        if (!CommFunAndroid.isNullOrEmpty(path)) {
            this.img_head.setImageURI(Uri.parse(path));
        }
    }

    private CropOptions getCropOptions(int width, int height) {


        boolean withWonCrop = true;//false;

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);//尺寸

//        if (rgCropSize.getCheckedRadioButtonId() == R.id.rbAspect) {
//            builder.setAspectX(width).setAspectY(height);//尺寸
//        } else {
//            builder.setOutputX(width).setOutputY(height);//比例
//        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

    /**
     * 配置是否压缩
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        //takePhoto.onEnableCompress(null,false);//不压缩
        int maxSize = 10240;//b
        int width = 300;
        int height = 300;
        boolean showProgressBar = true;//是否显示压缩进度条rgShowProgressBar.getCheckedRadioButtonId() == R.id.rbShowYes ? true : false;
        boolean enableRawFile = false;//拍照压缩后是否保存原图rgRawFile.getCheckedRadioButtonId() == R.id.rbRawYes ? true : false;

        CompressConfig config;

        int compressType = 1;

        if (compressType == 1) {//自带压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {//Luban压缩
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//是否使用TakePhoto自带相册
        builder.setCorrectImage(true);//是否纠正拍照的照片旋转角度

        takePhoto.setTakePhotoOptions(builder.create());

    }
}
