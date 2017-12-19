package custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import api.model.Banner;
import common.image.ImageLoader;
import common.image.ImageLoaderXutils3;

/**
 * banner滚动效果(ScrollView中使用有事件冲突)
 *
 * @author yangshiyou
 */
public class SlideShowViewAuto extends FrameLayout {

    /**
     * 存放打开类型，约人看电影项目使用
     */
    HashMap<String, Banner> openInfo = new HashMap<String, Banner>();

    /**
     * key:图片url,value:图片点击超链接
     */
    HashMap<String, String> imagesMap = new HashMap<String, String>();

    /**
     * 自定义轮播图的资源
     */
    private List<String> imageUrls = new ArrayList<String>();

    /**
     * 设置轮播图资源
     *
     * @param imageUrls
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;

        if (imageUrls != null && imageUrls.size() > 0) {
            // initUI();
            bindBanner();
        }
    }

    public void setOpenInfos(HashMap<String, Banner> bannerMap) {
        this.openInfo = bannerMap;
    }

    /**
     * 设置轮播图片资源点击超链接
     *
     * @param imageMap
     */
    public void setImagesMap(HashMap<String, String> imageMap) {
        this.imagesMap = imageMap;
    }

    // 自动轮播启用开关
    private final static boolean isAutoPlay = true;

    /**
     * 放轮播图片的ImageView 的list
     */
    private List<ImageView> imageViewsList = new ArrayList<ImageView>();
    /**
     * 放圆点的View的list
     */
    private List<View> dotViewsList = new ArrayList<View>();

    private ViewPager viewPager;

    private MyPagerAdapter myPagerAdapter;

    /**
     * 当前轮播页
     */
    private int currentItem = 0;

    private Context mContext;

    ImageLoader imageLoader;

    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 加载时默认显示图片资源ID
     */
    int loadingImageResId = R.mipmap.ic_launcher;
    /**
     * 加载失败时显示图片资源ID
     */
    int loadfailImageResid = R.mipmap.ic_launcher;

    /**
     * 设置加载时默认显示图片资源ID
     *
     * @param loadingImageResId
     */
    public void setLoadingImageResId(int loadingImageResId) {
        this.loadingImageResId = loadingImageResId;
    }

    /**
     * 设置加载失败时显示图片资源ID
     *
     * @param loadfailImageResid
     */
    public void setLoadfailImageResid(int loadfailImageResid) {
        this.loadfailImageResid = loadfailImageResid;
    }

    public SlideShowViewAuto(Context context) {
        this(context, null);

    }

    public SlideShowViewAuto(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideShowViewAuto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        imageLoader = ImageLoaderXutils3.getInstance(mContext);

        initUI();
        initData();
        bindBanner();

        if (isAutoPlay) {
            startPlay();
        }

    }

    /**
     * 开始轮播图切换
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        imageViewsList = new ArrayList<ImageView>();

        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    View slideshow_layoutView;

    LinearLayout dotLayout;
    LinearLayout.LayoutParams dotView_params;
    ImageView dotView;

    /**
     * 初始化Views等UI
     */
    private void initUI() {

        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();

        if (slideshow_layoutView == null) {
            if (mContext != null)
                slideshow_layoutView = LayoutInflater.from(mContext).inflate(R.layout.slideshow_layout, this, true);
        }
        if (dotLayout == null)
            dotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        if (dotView_params == null) {
            dotView_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dotView_params.leftMargin = 4;
            dotView_params.rightMargin = 4;
        }
        if (viewPager == null)
            viewPager = (ViewPager) findViewById(R.id.viewPager);

        if (viewPager != null) {
            viewPager.setFocusable(true);

            viewPager.setOnPageChangeListener(new MyPageChangeListener());

        }

    }

    private void bindBanner() {
        try {
            if (imageUrls == null || imageUrls.size() == 0) {
                return;
            }

            if (dotLayout != null)
                dotLayout.removeAllViews();

            imageViewsList = new ArrayList<ImageView>();
            dotViewsList = new ArrayList<View>();

            if (mContext != null) {

                // 热点个数与图片特殊相等
                for (int i = 0; i < imageUrls.size(); i++) {
                    ImageView view = new ImageView(mContext);
                    view.setTag(imageUrls.get(i));
                    // if (i == 0)// 给一个默认图
                    // view.setBackgroundResource(R.drawable.ic_launcher);
                    view.setScaleType(ScaleType.CENTER);// ScaleType.FIT_XY
                    imageViewsList.add(view);

                    dotView = new ImageView(mContext);
                    if (dotLayout != null)
                        dotLayout.addView(dotView, dotView_params);
                    dotViewsList.add(dotView);
                }

                changedotViews(0);
            }

            if (viewPager != null) {
                myPagerAdapter = new MyPagerAdapter();
                viewPager.setAdapter(myPagerAdapter);
                myPagerAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            bindBanner();

        }
    };

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(View container, int position) {

            if (imageViewsList != null && imageViewsList.size() > position) {

                ImageView imageView = imageViewsList.get(position);

                if (imageView != null) {

                    imageLoader.displayImage(imageView, imageView.getTag() + "", 480, 340);

                    ((ViewPager) container).addView(imageView);

                    imageView.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            try {
                                String url = "";
                                if (v != null)
                                    url = v.getTag() + "";

                                String linkurl = "";
                                Banner bannerMap = null;
                                if (imagesMap != null && imagesMap.containsKey(url))
                                    linkurl = imagesMap.get(url);

                                if (openInfo != null && openInfo.containsKey(url))
                                    bannerMap = openInfo.get(url);

                                if (listener != null) {
                                    listener.imgeClick(url, linkurl);
                                    listener.imageClick(url, bannerMap);
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                        }
                    });

                }

                return imageView;
            }
            return null;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {

            if (imageViewsList != null && imageViewsList.size() > position) {
                ImageView imageView = imageViewsList.get(position);
                if (imageView != null) {

                    ((ViewPager) container).removeView(imageView);
                }
            }
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    /**
     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {

            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {

            currentItem = pos;
            changedotViews(pos);
        }

    }

    public void changedotViews(int pos) {
        if (dotViewsList != null && dotViewsList.size() > 0) {
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewsList.get(pos)).setBackgroundResource(R.mipmap.dot_focus);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.mipmap.dot_blur);
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (viewPager != null && myPagerAdapter != null) {

        }
    }

    // Handler
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     */
    public void destoryBitmaps() {
        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();

                if (drawable != null) {

                    drawable.setCallback(null);// 解除drawable对view的引用

                    try {

                        Bitmap b = ((BitmapDrawable) drawable).getBitmap();

                        imageView.setImageBitmap(null);
                        if (!b.isRecycled() && null != b)
                            b.recycle();// 三星 galaxy
                        // s4等机型加载图片需手动回收bitmap,不然会导致oom
                    } catch (Exception e) {
                        String msg = e.getMessage();
                    }
                }
            }
        }

        try {
            this.removeAllViews();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private SlideClickListener listener;

    public interface SlideClickListener {

        public void imgeClick(String imgurl, String linkurl);

        public void imageClick(String imgurl, Banner banner);

    }

    public void setSlideClickListener(SlideClickListener clickListener) {
        this.listener = clickListener;
    }
}