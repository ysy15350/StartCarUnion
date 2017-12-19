package common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import custom_view.dialog.LoadingDialog;


public class CommFunMessage {

    private static final String TAG = "CommFunMessage";

    public static Context mContext;

    private static long lastClickTime;

    private static long lastShowTime;

    private static long lastFreshTime;

    /**
     * 防止多次点击
     *
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止频繁提示相同消息
     *
     * @return
     */
    public synchronized static boolean isFastShow() {
        long time = System.currentTimeMillis();
        if (time - lastShowTime < 3000) {
            return true;
        }
        lastShowTime = time;
        return false;
    }

    /**
     * 防止频繁刷新
     *
     * @return
     */
    public synchronized static boolean isFastFresh() {
        long time = System.currentTimeMillis();
        if (time - lastFreshTime < 10000) {
            return true;
        }
        lastFreshTime = time;
        return false;
    }

    private static String m_message = "";

    /**
     * 系统提示
     *
     * @param message
     */
    public static void showMsgBox(String message) {

        try {
            if (mContext != null) {
                Log.d(TAG, "showMsgBox() called with: message = [" + message + "]" + mContext);
                if (isFastShow() && m_message.equals(message)) {
                    return;
                }

                m_message = message;

                Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMsgBox(Context context, String message) {

        try {
            if (context != null) {
                mContext = context;
                Log.d(TAG, "showMsgBox() called with: message = [" + message + "]" + context);
                if (isFastShow() && m_message.equals(message)) {
                    return;
                }

                m_message = message;

                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 系统提示
     *
     * @param pic_id
     */
    public static void showImgBox(int pic_id) {

        try {

            if (mContext != null) {
                // 创建新Toast对象
                Toast showImageToast = new Toast(mContext);

                // 创建新ImageView对象
                ImageView imageView = new ImageView(mContext);

                // 从资源中获取图片
                imageView.setImageResource(pic_id);

                // 设置Toast上的View(imageView)
                showImageToast.setView(imageView);

                // 设置Toast显示时间
                showImageToast.setDuration(Toast.LENGTH_SHORT);

                // 显示Toast
                showImageToast.show();
            }
        } catch (Exception e) {

        }

    }

    private static LoadingDialog loadingDialog;

    public static void showWaitDialog(Activity activity,String msg) {
        hideWaitDialog();
        if (activity != null) {
            Log.d(TAG, "showWaitDialog() called with: msg = [" + msg + "]" + mContext);
            loadingDialog = new LoadingDialog(activity, msg, false);
            loadingDialog.show();
        }
    }

    public static void showWaitDialog(Context context, String msg) {
        hideWaitDialog();
        if (context != null) {
            mContext = context;
            Log.d(TAG, "showWaitDialog() called with: msg = [" + msg + "]" + context);
            loadingDialog = new LoadingDialog(context, msg, false);
            loadingDialog.show();
        }
    }

    public static void hideWaitDialog() {

        try {
            if (loadingDialog != null) {
                loadingDialog.hide();
                loadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}