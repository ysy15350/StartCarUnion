package com.ysy15350.startcarunion.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.ysy15350.startcarunion.R;

/**
 * 清除缓存
 * 
 * @author yangshiyou
 *
 */
public class ClearCacheDialog extends Dialog {

	private Context mContext;

	private View conentView;

	private View ll_close, btn_cancel, btn_ok;

	public ClearCacheDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		mContext = context;

		initView();// 初始化按钮事件

	}

	private void initView() {

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		conentView = inflater.inflate(R.layout.dialog_clear_cache, null);

		ll_close = conentView.findViewById(R.id.ll_close);
		btn_cancel = conentView.findViewById(R.id.btn_cancel);
		btn_ok = conentView.findViewById(R.id.btn_ok);

		ll_close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onCancelClick();
				}
				dismiss();
			}
		});

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
				if (mListener != null) {
					mListener.onOkClick();
				}
				dismiss();
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
	 *
	 */
	public interface DialogListener {

		public void onCancelClick();

		public void onOkClick();

	}

}
