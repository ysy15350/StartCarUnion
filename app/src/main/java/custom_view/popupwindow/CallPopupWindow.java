package custom_view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_Call;

import java.util.List;

public class CallPopupWindow extends PopupWindow {

    private Activity mContext;

    private List<String> mPhones;

    private View conentView;

    private View ll_close;

    private ListView lv_btn;


    public CallPopupWindow(final Activity context, List<String> phones) {

        mContext = context;
        mPhones = phones;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_call, null);

        init();
        initView();// 初始化按钮事件

    }

    private void init() {
        conentView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        int h = mContext.getWindowManager().getDefaultDisplay().getHeight();
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // this.setBackgroundDrawable(dw);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.app_pop);
    }

    private void initView() {

        ll_close = conentView.findViewById(R.id.ll_close);

        lv_btn = (ListView) conentView.findViewById(R.id.lv_btn);

        lv_btn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListener != null) {
                    mListener.onCallMobile(adapterView.getItemAtPosition(i).toString());
                }
                dismiss();
            }
        });

        ListViewAdpater_Call adpater_call = new ListViewAdpater_Call(mContext, mPhones);
        lv_btn.setAdapter(adpater_call);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        } else {
            this.dismiss();
        }
    }

    /**
     * 点击监听
     */
    private PopListener mListener;

    /**
     * 设置popupwindow中按钮监听
     *
     * @param listener
     */
    public void setPopListener(PopListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击事件监听
     *
     * @author yangshiyou
     */
    public interface PopListener {

        public void onCallMobile(String mobile);

        public void onCancelClick();

    }

}
