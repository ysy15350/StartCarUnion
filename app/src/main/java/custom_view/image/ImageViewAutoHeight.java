package custom_view.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * 根据宽度自动计算图片高度并显示
 *
 * @author okmacopid
 */
public class ImageViewAutoHeight extends ImageView implements OnGlobalLayoutListener {

    /**
     * 是否已经初始化
     */
    private boolean mOnce;

    /**
     * 初始化时缩放的值
     */
    private float mInitScale;

    private Matrix matrix;

    public ImageViewAutoHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        matrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

    }

    public ImageViewAutoHeight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public ImageViewAutoHeight(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    /**
     * 当view atached到屏幕上时
     */
    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 当view从屏幕消除时
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        try {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);// 最小API支持
            // 16
            getViewTreeObserver().removeGlobalOnLayoutListener(this);

        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            Drawable drawable = getDrawable();
            if (drawable != null) {

                Bitmap b = ((BitmapDrawable) drawable).getBitmap();

                if (!b.isRecycled() && null != b)
                    b.recycle();// 三星 galaxy
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * 布局完成后调用,获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            // 控件宽高
            int width = getWidth();
            int height = getHeight();

            // 得到图片，及宽高
            Drawable drawable = getDrawable();

            if (drawable == null)
                return;

            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();

            float scale = 1.0f;

            scale = width * 1.0f / drawableWidth;

            mInitScale = scale;

            int dx = 0;
            int dy = 0;

            RectF rect = getMatrixRectF();
            if (rect != null) {
                dx = (int) rect.left;
                dy = (int) rect.top;
            }

            // matrix.postTranslate(dx, dy);
            matrix.postScale(mInitScale, mInitScale, dx, dy);

            setImageMatrix(matrix);

            mOnce = true;
        }

    }

    /**
     * 获取图片缩放至屏幕宽度，高度等比缩放后的值
     */
    private int getBitmapNewHeight() {
        try {

            Drawable drawable = getDrawable();

            int drableWidth = getWidth();

            int drableHeight = getHeight();

            if (drawable == null)
                return 0;

            if (getWidth() == 0 || getHeight() == 0)
                return 0;

            this.measure(0, 0);

            if (drawable.getClass() == NinePatchDrawable.class)
                return 0;

            Bitmap b = ((BitmapDrawable) drawable).getBitmap();

            Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

            if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0)
                return 0;

            // scale = (float) drableWidth / (float) bitmap.getWidth();

            drableHeight = Math.round(bitmap.getHeight() * mInitScale);

            return drableHeight;

        } catch (Exception e) {
            // TODO: handle exception
        }

        return 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        this.measure(0, 0);

        int drableHeight = getBitmapNewHeight();

        LayoutParams params = this.getLayoutParams();

        params.width = getWidth();// 屏幕宽度

        params.height = drableHeight;

        this.setLayoutParams(params);

        super.onDraw(canvas);

    }

    /**
     * 获取当前图片的缩放的值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 获取图片放大、缩小之后的宽高。以及left,right,top,bottom
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = this.matrix;

        RectF rectF = new RectF();

        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        return rectF;
    }

}
