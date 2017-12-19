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

public class ListViewAdpater_MyContacts extends CommonAdapter<StoreInfo> {

    public ListViewAdpater_MyContacts(Context context, List<StoreInfo> list) {
        super(context, list, R.layout.list_item_my_contacts);

    }

    @Override
    public void convert(final ViewHolder holder, final StoreInfo t) {

        if (t != null) {
            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getIcon(), true);
            holder
                    .setText(R.id.tv_fullName, t.getFullname())
                    .setText(R.id.tv_phone, t.getTell());
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
