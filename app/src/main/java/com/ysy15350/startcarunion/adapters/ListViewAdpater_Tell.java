package com.ysy15350.startcarunion.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.ysy15350.startcarunion.R;

import java.util.List;

import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 我的消息
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Tell extends CommonAdapter<String> {

    public ListViewAdpater_Tell(Context context, List<String> list) {
        super(context, list, R.layout.list_item_tell);

    }

    @Override
    public void convert(ViewHolder holder, final String t) {
        if (t != null) {
            holder.getView(R.id.img_edit).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.editTell(t);
                    }
                }
            });

            holder.getView(R.id.img_del).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.deleteTell(t);
                    }
                }
            });

            holder.setText(R.id.tv_phone, t);

        }

    }

    private AdapterListener mListener;

    public void setListener(AdapterListener listener) {
        mListener = listener;
    }

    public interface AdapterListener {

        public void editTell(String tell);

        public void deleteTell(String tell);

    }

}
