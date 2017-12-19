package common.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ysy15350.startcarunion.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

public class ImageLoaderXutils3 implements ImageLoader {

    /**
     * 单例
     */
    private static ImageLoaderXutils3 imageLoader;

    /**
     * 上下文
     */
    private static Context mContext;

    /**
     * 加载图片参数对象
     */
    private static ImageOptions imageOptions;

    /**
     * 图片宽度
     */
    private static int width = 50;

    /**
     * 图片高度
     */
    private static int height = 50;

    /**
     * 正在加载时显示图片（默认图片）
     */
    private static int loadingImageResId = R.mipmap.ic_launcher;

    /**
     * 加载失败时显示图片
     */
    private static int loadfailImageResId = R.mipmap.ic_launcher;

    /**
     * 私有构造方法，保证不能被实例化
     */
    private ImageLoaderXutils3() {

    }

    /**
     * 获取imageLoader对象,外部使用此实例对象处理图片
     *
     * @param context
     * @return
     */
    public static ImageLoaderXutils3 getInstance(Context context) {
        // 这里可以保证只实例化一次
        // 即在第一次调用时实例化
        // 以后调用便不会再实例化
        if (imageLoader == null) {
            imageLoader = new ImageLoaderXutils3();
        }

        if (context != null)
            mContext = context;

        // init();

        return imageLoader;
    }

    private static int radius;

    private static boolean circular;

    private static boolean crop = true;

    private static boolean ignoreGif = false;

    /**
     * 初始化参数
     */
    private static void init() {

        ImageOptions.Builder builder = new ImageOptions.Builder();

        builder = new ImageOptions.Builder()
                .setConfig(Bitmap.Config.RGB_565)//设置图片质量(默认：RGB_565)
                .setRadius(radius)
                .setCircular(circular)
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                //.setCrop(crop) // 是否对图片进行裁剪,很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)//MATRIX
                // 是否忽略GIF格式的图片
                .setIgnoreGif(ignoreGif)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setUseMemCache(true)//设置使用缓存
                .setLoadingDrawableId(loadingImageResId)
                .setFailureDrawableId(loadfailImageResId);
        if (width != 0 && height != 0) {
            builder.setSize(DensityUtil.dip2px(width), DensityUtil.dip2px(height));
        }

        imageOptions = builder.build();
    }


    @Override
    public void displayImage(ImageView imageView, String url) {


        init();

        x.image().bind(imageView, url, imageOptions);// 本地图片


        //显示完，还原
        ImageLoaderXutils3.loadingImageResId = R.mipmap.ic_launcher;
        ImageLoaderXutils3.loadfailImageResId = R.mipmap.ic_launcher;
        ImageLoaderXutils3.width = 50;
        ImageLoaderXutils3.height = 50;

        ImageLoaderXutils3.circular = false;
    }


    @Override
    public void displayImage(ImageView imageView, String url, boolean circular) {
        // TODO Auto-generated method stub
        ImageLoaderXutils3.circular = circular;

        ImageLoaderXutils3.loadingImageResId = R.mipmap.icon_head;
        ImageLoaderXutils3.loadfailImageResId = R.mipmap.icon_head;

        displayImage(imageView, url);
    }

    @Override
    public void displayImage(ImageView imageView, String url, int width, int height, boolean circular) {
        // TODO Auto-generated method stub
        ImageLoaderXutils3.circular = circular;

        ImageLoaderXutils3.loadingImageResId = R.mipmap.icon_head;
        ImageLoaderXutils3.loadfailImageResId = R.mipmap.icon_head;
        ImageLoaderXutils3.width = width;
        ImageLoaderXutils3.height = height;

        displayImage(imageView, url);
    }


    @Override
    public void displayImage(ImageView imageView, String url, int width, int height) {

        ImageLoaderXutils3.loadingImageResId = R.mipmap.ic_launcher;
        ImageLoaderXutils3.loadfailImageResId = R.mipmap.ic_launcher;


        ImageLoaderXutils3.width = width;
        ImageLoaderXutils3.height = height;

        ImageLoaderXutils3.circular = false;

        displayImage(imageView, url);
    }

    @Override
    public void destoryImageView() {


    }
}