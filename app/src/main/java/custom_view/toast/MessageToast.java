package custom_view.toast;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ysy15350.startcarunion.R;

/**
 * 确认对话框
 *
 * @author yangshiyou
 */
public class MessageToast {

    Toast mToast;

    private Context mContext;

    TextView tv_message;

    ImageView img_icon;

    int mPic_id;
    String mContent;

    public static MessageToast makeText(Context context, int pic_id, String msg) {
        return new MessageToast(context, pic_id, msg);
    }

    public MessageToast(Context context, String msg) {

        this(context, R.mipmap.icon_success, msg);
        //Toast.makeText(MyService.this, Config.AUTHORIZTION_TMEP + "myService  getToken" + token, Toast.LENGTH_SHORT).show();

    }

    public MessageToast(Context context, int pic_id, String msg) {

        mContext = context;

        mPic_id = pic_id;
        mContent = msg;

        initView();// 初始化按钮事件

    }

    private void initView() {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        ViewGroup rootView = (ViewGroup) mInflater.inflate(R.layout.toast_message, null);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        TextView tv_message = (TextView) rootView.findViewById(R.id.tv_message);
        if (!mContent.equals(""))
            tv_message.setText(mContent);
        ImageView img_icon = (ImageView) rootView.findViewById(R.id.img_icon);
        if (img_icon != null && mPic_id != 0) {
            img_icon.setImageResource(mPic_id);
        }

        // 创建新Toast对象
        mToast = new Toast(mContext);

        // 设置Toast上的View(imageView)
        mToast.setView(rootView);

        // 设置Toast显示时间
        mToast.setDuration(Toast.LENGTH_SHORT);
        // showImageToast.setGravity(Gravity.TOP, 0, 200);

    }

    public void show() {
        mToast.show();
    }

}
