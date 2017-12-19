package com.ysy15350.startcarunion.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.StoreInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

public class ListViewAdpater_MyFootprint extends CommonAdapter<StoreInfo> {

    public ListViewAdpater_MyFootprint(Context context, List<StoreInfo> list) {
        super(context, list, R.layout.list_item_my_footprint);

    }

    @Override
    public void convert(final ViewHolder holder, final StoreInfo t) {

        if (t != null) {

            String date = t.getDate();
            holder.setVisibility_GONE(R.id.tv_date);
            if (!CommFunAndroid.isNullOrEmpty(date)) {
                holder.setVisibility_VISIBLE(R.id.tv_date).setText(R.id.tv_date, t.getDate());
            }

            holder
                    .setImageURL(R.id.img_head,
                            Config.getServer() + t.getLitpic(), 126, 126,
                            true);
            holder.setText(R.id.tv_fullName, t.getFullname())
                    .setText(R.id.tv_pid, "主营：" + t.getPid())
                    .setText(R.id.tv_phone, t.getMobile());
        }

        /**
         * 拨打电话
         */
        holder.getView(R.id.ll_call).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (mAdapterListener != null) {
                    mAdapterListener.onCall(t);
                }

            }
        });

    }

    public interface AdapterListener {
        public void onCall(StoreInfo storeInfo);
    }

    private AdapterListener mAdapterListener;

    public void setAdapterListener(AdapterListener adapterListener) {
        this.mAdapterListener = adapterListener;
    }

}
