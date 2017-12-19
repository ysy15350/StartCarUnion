package common.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import common.cache.ACache;

public class ImageUtil {

    private static ImageUtil instance;

    private static Context mContext;

    /**
     * 缓存工具类
     */
    private static ACache mCache;

    private static LruCache<String, Bitmap> mLruCache;

    private ImageUtil() {
    }

    public static synchronized ImageUtil getInstance() {

        if (instance == null) {
            instance = new ImageUtil();
        }

        init();

        return instance;
    }

    /**
     * @param context
     * @return
     */
    public static synchronized ImageUtil getInstance(Context context) {

        if (context != null)
            mContext = context;

        return getInstance();
    }

    /**
     * 初始化
     */
    private static void init() {
        if (mCache == null) {
            if (mContext != null)
                mCache = ACache.get(mContext);
        }

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // TODO Auto-generated method stub

                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 获取本地图片Bitmap
     *
     * @param resId 资源ID
     * @return
     */
    public Bitmap GetLocalBitmap(Context context, int resId) {

        try {
            Options opt = new Options();

            opt.inPreferredConfig = Config.RGB_565;

            opt.inPurgeable = true;

            opt.inInputShareable = true;

            Bitmap bitmap = null;

            // 获取资源图片

            // InputStream is = null;

            // is = mContext.getResources().openRawResource(resId);

            // bitmap = BitmapFactory.decodeStream(is, null, opt);

            // if (null != is)
            // is.close();

            if (context != null)
                bitmap = BitmapFactory.decodeResource(context.getResources(), resId);

            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }

    /**
     * 获取图片Drawable首先获取缓存，如果缓存为空获取资源id的图片
     *
     * @param drawId
     * @return
     */
    public Drawable getDrawable(int drawId) {
        try {
            Drawable drawable = getCacheDrawable(drawId + "");

            if (drawable == null) {
                drawable = GetLocalBitmapDrawable(drawId);
            }

            return drawable;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 获取本地图片
     *
     * @param resId
     * @return
     */
    public BitmapDrawable GetLocalBitmapDrawable(int resId) {
        try {

            if (mContext == null)
                return null;

            Bitmap bitmap = GetLocalBitmap(resId);
            if (bitmap == null)
                return null;

            BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);

            return bitmapDrawable;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 获取图片缓存Drawable
     *
     * @param key
     * @return
     */
    public Drawable getCacheDrawable(String key) {
        try {
            if (mCache != null)
                return mCache.getAsDrawable(key);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 获取本地Bitmap首先获取缓存，如果缓存为空获取资源id的图片(存入缓存)
     *
     * @param drawId
     * @return
     */
    public Bitmap getBitmap(int drawId) {
        try {
            Bitmap bitmap = getCacheBitmap(drawId + "");

            if (bitmap == null) {
                bitmap = GetLocalBitmap(drawId);
                putCacheBitmap(drawId + "", bitmap);
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取图片缓存
     *
     * @param path
     * @return
     */
    public Bitmap getBitmapFromLruCache(String path) {

        Bitmap bitmap = mLruCache.get(path);

        if (bitmap == null && mCache != null)
            bitmap = mCache.getAsBitmap(path);

        return bitmap;
    }

    public void addBitmapToLruCache(String path, Bitmap bm) {

        if (getBitmapFromLruCache(path) == null) {
            if (bm != null) {
                mLruCache.put(path, bm);
                if (mCache != null)
                    mCache.put(path, bm);
            }
        }
    }

    /**
     * 缓存图片Bitmap
     *
     * @param path   图片地址
     * @param bitmap 图片Bitmap
     */
    public void putCacheBitmap(String key, Bitmap bitmap) {
        try {
            if (mCache != null)
                mCache.put(key, bitmap);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 获取图片缓存Bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getCacheBitmap(String key) {
        try {
            return mCache.getAsBitmap(key);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    /**
     * 获取本地图片Bitmap
     *
     * @param resId 资源ID
     * @return
     */
    public Bitmap GetLocalBitmap(int resId) {

        try {
            Options opt = new Options();

            opt.inPreferredConfig = Config.RGB_565;

            opt.inPurgeable = true;

            opt.inInputShareable = true;

            Bitmap bitmap = null;

            if (mContext != null)
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);

            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }

    /**
     * 将图片转为byte数组
     *
     * @param bmp         Bitmap
     * @param needRecycle
     * @return
     */
    public byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * /** 图片压缩
     *
     * @param image bitmap原图
     * @param type  jpg png
     * @return 压缩后的bitmap
     */
    public Bitmap compressImage(Bitmap image, String type) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(type.equals("jgp") ? CompressFormat.JPEG : CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

            int options = 100;
            while (baos.toByteArray().length / 1024 > 50) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                options -= 10;// 每次都减少10
                image.compress(type.equals("jgp") ? CompressFormat.JPEG : CompressFormat.PNG, options,
                        baos);// 这里压缩options%，把压缩后的数据存放到baos中

            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中

            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap, "jpg");// 压缩好比例大小后再进行质量压缩
    }

    /**
     * @param img_url
     * @param width
     * @param height
     * @return
     */
    @SuppressLint("HandlerLeak")
    public Bitmap blur(final String path, final View view, final int width, final int height) {
        try {

            if (view != null)
                view.setTag(path);

            final Handler handler = new Handler() {
                @SuppressLint("NewApi")
                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);

                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    if (holder != null) {
                        Bitmap bm = holder.bitmap;
                        View imageView = holder.view;
                        String path = holder.path;

                        // String path_1 = imageView.getTag().toString();

                        if (imageView.getTag().toString().equals(path)) {
                            imageView.setBackground(new BitmapDrawable(mContext.getResources(), bm));
                        }
                    }

                }
            };

            new Thread() {

                @Override
                public void run() {

                    // 从缓存读取已经高斯模糊处理过的Bitmap
                    Bitmap blur_bitmap = getCacheBitmap(path + "blur");

                    if (blur_bitmap == null) {

                        // 读取原图片缓存
                        Bitmap bitmap = getBitmap(path);

                        if (bitmap != null) {
                            blur_bitmap = blur(bitmap, width, height);
                            if (blur_bitmap != null)
                                mLruCache.put(path + "blur", blur_bitmap);
                        }
                    }

                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = blur_bitmap;
                    holder.path = path;
                    holder.view = view;

                    Message message = Message.obtain();

                    message.obj = holder;

                    handler.sendMessage(message);
                }

                ;

            }.start();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 根据url获取bitmap，需要在线程中读取
     *
     * @param path
     * @return
     */
    public Bitmap getBitmap(String path) {

        try {
            Bitmap bitmap = getCacheBitmap(path);

            if (bitmap != null)
                return bitmap;

            byte[] data = getImage(path);

            return BitmapFactory.decodeByteArray(data, 0, data.length);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getBitmap(InputStream inputStream) {

        try {

            byte[] data = readInputStream(inputStream);

            return BitmapFactory.decodeByteArray(data, 0, data.length);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public InputStream getImageInputStream(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 设置请求方法为GET
        conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
        return inputStream;
    }

    public byte[] getImage(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 设置请求方法为GET
        conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
        byte[] data = readInputStream(inputStream); // 获得图片的二进制数据
        return data;
    }

    /*
     * 从数据流中获得数据
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();

    }

    /**
     * 高斯模糊处理
     *
     * @param bkg
     * @param view
     */
    @SuppressLint("NewApi")
    public Bitmap blur(Bitmap bkg, int width, int height) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;// 图片缩放比例；
        float radius = 80;// 模糊程度

        Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        // canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() /
        // scaleFactor);

        canvas.translate(0, 0);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);

        return overlay;

        // view.setBackground(new BitmapDrawable(getActivity().getResources(),
        // overlay));
        /**
         * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，
         * 可是将scaleFactor设置大一些。
         */
        // Log.i("jerome", "blur time:" + (System.currentTimeMillis() -
        // startMs));
    }

    /**
     * 根据图片需要显示的宽和高对图片进行压缩
     *
     * @param path
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public Bitmap decodeSampledBitmapFromPath(String path, int width, int height) throws IOException {

        // 获得图片的宽和高，并 不把 图片加载到内存中
        Options options = new Options();
        options.inJustDecodeBounds = true;

        // Bitmap bmp = BitmapFactory.decodeFile(path, options);

        Bitmap bitmap = null;

        try {
            byte[] data = getImage(path);
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            // bitmap = BitmapFactory.decodeFile(path, options);

            options.inSampleSize = caculateInSampleSize(options, width, height);

            // 使用获取到的inSampleSize再次解析图片
            options.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    /**
     * 根据需求的宽和高及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param width
     * @param height
     * @return
     */
    private int caculateInSampleSize(Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);

        }

        return inSampleSize;
    }

    /**
     * 获取适当的压缩的宽和高
     *
     * @param imageView
     * @return
     */
    public ImageSize getImageViewSize(ImageView imageView) {

        ImageSize imageSize = new ImageSize();

        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        LayoutParams lp = imageView.getLayoutParams();

        int width = imageView.getWidth();

        if (width <= 0) {
            width = lp.width;// 获取ImageView在Layout中声明的宽度
        }

        if (width <= 0) {
            // width = imageView.getMaxWidth();// 检查最大值
            width = getImgaeViewFieldValue(imageView, "mMaxWidth");// 解决getMaxWidth()方法兼容性问题
        }

        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();

        if (height <= 0) {
            height = lp.height;// 获取ImageView在Layout中声明的宽度
        }

        if (height <= 0) {
            // height = imageView.getMaxHeight();// 检查最大值

            height = getImgaeViewFieldValue(imageView, "mMaxHeight");
        }

        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    /**
     * 通过反射机制获取ImageView 属性值
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static int getImgaeViewFieldValue(Object object, String fieldName) {

        int value = 0;

        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return value;

    }

    /**
     * 建议都调用此方法
     *
     * @param imageView
     */
    public void destoryImageView(ImageView imageView) {
        try {
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();

                if (drawable != null) {

                    drawable.setCallback(null);// 解除drawable对view的引用
                    Bitmap b = ((BitmapDrawable) drawable).getBitmap();
                    imageView.setImageBitmap(null);
                    if (!b.isRecycled() && null != b)
                        b.recycle();// 三星 galaxy
                    // s4等机型加载图片需手动回收bitmap,不然会导致oom
                }
            }

            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * /** 保存图片默认保存Download文件夹
     *
     * @param bitNamed    保存图片的文件名称不包含后缀名,
     * @param mBitmap     保存的Bitmap
     * @param imageFolder 保存图片的目标路径，默认保存到Download文件夹
     */
    public static File SaveBitmap(String bitName, Bitmap mBitmap, String imageFolder) {
        try {
            if (imageFolder == null || "".equals(imageFolder))
                imageFolder = Environment.getExternalStorageDirectory() + "/Download";

            String imagePath = imageFolder + "/" + bitName + ".png";

            File f = new File(imagePath);
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mBitmap.compress(CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        View view;
        String path;
    }

}
