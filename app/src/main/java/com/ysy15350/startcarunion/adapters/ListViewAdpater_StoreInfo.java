package com.ysy15350.startcarunion.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.StoreInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

public class ListViewAdpater_StoreInfo extends CommonAdapter<StoreInfo> {

    private int height = 0;

    public ListViewAdpater_StoreInfo(Context context, List<StoreInfo> list, int item_height) {
        super(context, list, R.layout.list_item_store_info);
        height = item_height;
        if (item_height == 0) {
            height = CommFunAndroid.dip2px(200);
        }

    }

    public ListViewAdpater_StoreInfo(Context context, List<StoreInfo> list) {
        this(context, list, 0);

    }

    @Override
    public void convert(final ViewHolder holder, final StoreInfo t) {


        if (t != null) {

            LinearLayout ll_main = holder.getView(R.id.ll_main);
            if (ll_main != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_main.getLayoutParams();
                layoutParams.height = this.height;
                ll_main.setLayoutParams(layoutParams);
            }

            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getIcon(),
                    84, 84);

            String mobile = t.getMobile();
            String tell = t.getTell();

            String showTell = (CommFunAndroid.isNullOrEmpty(tell) ? mobile : tell);


            holder
                    .setText(R.id.tv_fullName, t.getFullname())
                    .setText(R.id.tv_pid, "主营：" + t.getPid())
                    .setText(R.id.tv_phone, showTell)
            ;

            /**
             * 拨打电话
             */
            holder.getView(R.id.ll_call).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                    if (mAdapterListener != null) {
                        mAdapterListener.onCall(t);
                    }

                }
            });
        }


    }


    public interface AdapterListener {
        public void onCall(StoreInfo storeInfo);
    }

    private AdapterListener mAdapterListener;

    public void setAdapterListener(AdapterListener adapterListener) {
        this.mAdapterListener = adapterListener;
    }


}
