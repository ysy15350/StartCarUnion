package base.adapter;

/**
 * Created by yangshiyou on 2017/7/10.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.CommFunAndroid;
import common.CommFunMessage;
import common.image.ImageLoader;
import common.image.ImageLoaderXutils3;
import common.image.ImageUtil;


/**
 * CommonAdapter<T>的ViewHolder
 *
 * @author Administrator
 */
public class ViewHolder {

    private SparseArray<View> mViews;

    private int mPosition;
    private View mConvertView;

    private Context mContext;

    private ImageLoader imageLoader;

    /**
     * adapter使用
     *
     * @param context
     * @param viewGroup
     * @param layoutId
     * @param position
     */
    public ViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        this.mContext = context;
        this.imageLoader = ImageLoaderXutils3.getInstance(mContext);
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mConvertView.setTag(this);
    }

    /**
     * adapter使用
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        try {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            } else {
                ViewHolder holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
                return holder;
            }

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Activity使用
     *
     * @param context
     * @param convertView
     */
    public ViewHolder(Context context, View convertView) {
        this.mContext = context;
        this.mViews = new SparseArray<View>();
        mConvertView = convertView;
        mConvertView.setTag(this);
        this.imageLoader = ImageLoaderXutils3.getInstance(mContext);

    }

    /**
     * Activity使用
     *
     * @param context
     * @param convertView
     * @return
     */
    public static ViewHolder get(Context context, View convertView) {
        try {

            return new ViewHolder(context, convertView);

        } catch (Exception e) {

        }
        return null;
    }

    public <T extends View> T getView(int viewId) {
        try {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }

            return (T) view;
        } catch (Exception e) {

        }
        return null;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView文本
     *
     * @param viewId 控件ID
     * @param text   文本内容
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        try {
            View view = getView(viewId);
            setText(view, text);

        } catch (Exception e) {

        }

        return this;
    }

    public ViewHolder setText(View view, CharSequence text) {
        try {
            if (view != null && text != null) {
                if (view instanceof TextView) {
                    ((TextView) view).setText(text);
                }
                if (view instanceof TextSwitcher) {
                    ((TextSwitcher) view).setText(text);
                }
                // if (view instanceof EditText) {
                // ((TextView) view).setText(text);
                // }
                // if (view instanceof Button) {
                // ((TextView) view).setText(text);
                // }
            }

        } catch (Exception e) {

        }

        return this;
    }

    public String getViewText(int viewId) {
        try {
            View view = getView(viewId);
            if (view != null) {
                if (view instanceof TextView) {
                    return ((TextView) view).getText().toString();
                }
                if (view instanceof EditText) {
                    return ((EditText) view).getText().toString().trim();
                }
            }
        } catch (Exception e) {

        }

        return "";
    }

    public String getViewText(int viewId, boolean required, String msg) {
        try {
            String text = getViewText(viewId);
            if (CommFunAndroid.isNullOrEmpty(text) && required) {
                CommFunMessage.showMsgBox(msg);
                return "";
            }
            return text;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    /**
     * 设置控件背景图片
     *
     * @param viewId
     * @param drawable drawableId图片
     * @return
     */
    public ViewHolder setBackground(int viewId, int drawableId) {

        return this;
    }

    /**
     * 设置控件背景图片
     *
     * @param viewId
     * @param drawable drawableId图片
     * @return
     */
    public ViewHolder setBackgroundColor(int viewId, int color) {

        return this;
    }

    /**
     * 设置TextView文本颜色
     *
     * @param viewId
     * @param color  R.color.txt_color1
     * @return
     */
    public ViewHolder setTextColor(int viewId, int color) {
        try {
            TextView textView = getView(viewId);
            if (textView != null && color != 0) {
                textView.setTextColor(mContext.getResources().getColor(color));
            }
            View view = getView(viewId);
            if (view != null) {
                if (view instanceof TextView) {
                    ((TextView) textView).setTextColor(mContext.getResources().getColor(color));
                }
                if (view instanceof EditText) {
                    ((EditText) view).setTextColor(mContext.getResources().getColor(color));
                }
            }
        } catch (Exception e) {

        }

        return this;
    }

    /**
     * 设置tag
     *
     * @param viewId 控件ID
     * @param obj    tag内容
     * @return
     */
    public ViewHolder setTag(int viewId, Object obj) {
        try {
            View view = getView(viewId);
            if (view != null)
                view.setTag(obj);
        } catch (Exception e) {

        }
        return this;
    }

    /**
     * 设置ImageView图片
     *
     * @param viewId ImageView控件id
     * @param resId  图片ID
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {

        return this;
    }

    /**
     * 设置ImageView图片
     *
     * @param viewId ImageView控件id
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        addImageView(imageView);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置ImageView图片
     *
     * @param viewId   ImageView控件id
     * @param drawable
     * @return
     */
    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        addImageView(imageView);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        return this;
    }

    /**
     * 设置ImageView图片
     *
     * @param viewId ImageView控件id
     * @param url
     * @return
     */
    public ViewHolder setImageURL(int viewId, String url) {
        ImageView imageView = getView(viewId);
        addImageView(imageView);
        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url);
        }

        return this;
    }


    /**
     * z
     * 显示图片
     *
     * @param imageView
     * @param url
     * @return
     */
    public ViewHolder setImageURL(ImageView imageView, String url) {

        addImageView(imageView);

        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url);
        }

        return this;
    }

    public ViewHolder setImageURL(ImageView imageView, String url, int width, int height) {

        addImageView(imageView);

        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url, width, height);
        }

        return this;
    }

    public ViewHolder setImageURL(int viewId, String url, boolean circular) {
        ImageView imageView = getView(viewId);

        addImageView(imageView);

        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url, circular);
        }

        return this;
    }

    public ViewHolder setImageURL(int viewId, String url, int width, int height, boolean circular) {
        ImageView imageView = getView(viewId);

        addImageView(imageView);

        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url, width, height, circular);
        }

        return this;
    }

    /**
     * 显示图片
     *
     * @param viewId
     * @param url
     * @param width
     * @param height
     * @return
     */
    public ViewHolder setImageURL(int viewId, String url, int width, int height) {
        ImageView imageView = getView(viewId);

        addImageView(imageView);

        if (imageLoader != null && imageView != null && !CommFunAndroid.isNullOrEmpty(url)) {
            imageLoader.displayImage(imageView, url, width, height);
        }
        return this;
    }

    /**
     * 获取当前Holder所有显示的ImageView
     *
     * @return
     */
    public List<ImageView> getImageViewList() {
        return imageViewList;
    }

    /**
     * 添加到集合中
     *
     * @param imageView
     */
    private void addImageView(ImageView imageView) {
        if (imageView != null) {
            if (imageViewList == null) {
                imageViewList = new ArrayList<ImageView>();
            }
            if (!imageViewList.contains(imageView)) {
                imageViewList.add(imageView);
            }
        }

    }

    public ViewHolder setVisibility_VISIBLE(int viewId) {
        View view = getView(viewId);
        if (view != null)
            view.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewHolder setVisibility_GONE(int viewId) {
        View view = getView(viewId);
        if (view != null)
            view.setVisibility(View.GONE);
        return this;
    }

    List<ImageView> imageViewList = new ArrayList<>();

    /**
     * 回收ImageView
     *
     * @return
     */
    public ViewHolder destoryImageView() {
        try {
            if (imageViewList != null && !imageViewList.isEmpty()) {
                for (ImageView imgae :
                        imageViewList) {
                    if (imgae != null)
                        ImageUtil.getInstance(mContext).destoryImageView(imgae);
                }

                imageViewList = new ArrayList<>();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
