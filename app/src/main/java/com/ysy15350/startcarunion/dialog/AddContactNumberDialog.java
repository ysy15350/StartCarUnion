package com.ysy15350.startcarunion.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.ysy15350.startcarunion.R;

import common.CommFunAndroid;
import common.CommFunMessage;

/**
 * 签到成功
 *
 * @author yangshiyou
 */
public class AddContactNumberDialog extends Dialog {

    private Context mContext;

    private int mType;//1:添加   2:删除

    private String mTell;

    private View conentView;

    private EditText et_tell;

    private View btn_cancel, btn_ok;

    public AddContactNumberDialog(Context context) {
        this(context, "", 1);
    }


    public AddContactNumberDialog(Context context, String tell, int type) {
        super(context);
        this.mContext = context;
        mTell = tell;
        mType = type;

        initView();// 初始化按钮事件
    }


    private void initView() {

        try {
            if (mContext != null) {


                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                conentView = inflater.inflate(R.layout.dialog_add_contact, null);

                et_tell = conentView.findViewById(R.id.et_tell);
                if (!CommFunAndroid.isNullOrEmpty(mTell)) et_tell.setText(mTell);

                btn_cancel = conentView.findViewById(R.id.ll_close);
                btn_ok = conentView.findViewById(R.id.btn_ok);

                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mListener != null) {
                            mListener.onCancelClick();
                        }
                        dismiss();
                    }
                });

                btn_ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String tell = et_tell.getText().toString().trim();

                        if (!CommFunAndroid.isNullOrEmpty(tell)) {
                            if (mListener != null) {
                                mListener.onOkClick(tell, mType);
                            }
                            dismiss();
                        } else {
                            CommFunMessage.showMsgBox("手机号格式不正确");
                        }
                    }
                });

                // WindowManager.LayoutParams params = this.getWindow().getAttributes();
                // params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                // params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                // dialog.getWindow().setBackgroundDrawable(new
                // ColorDrawable(android.R.color.transparent));
                this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
                this.setCanceledOnTouchOutside(false);

                // 解决圆角黑边
                // getWindow().setBackgroundDrawable(new BitmapDrawable());
                // 或者
                getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                this.setContentView(conentView);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击监听
     */
    private DialogListener mListener;

    /**
     * 设置popupwindow中按钮监听
     *
     * @param listener
     */
    public void setDialogListener(DialogListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击事件监听
     *
     * @author yangshiyou
     */
    public interface DialogListener {

        public void onCancelClick();

        public void onOkClick(String tell, int type);

    }

}
