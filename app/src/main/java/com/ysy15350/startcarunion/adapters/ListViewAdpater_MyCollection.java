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

public class ListViewAdpater_MyCollection extends CommonAdapter<StoreInfo> {

    public ListViewAdpater_MyCollection(Context context, List<StoreInfo> list) {
        super(context, list, R.layout.list_item_my_collection);

    }

    @Override
    public void convert(final ViewHolder holder, final StoreInfo t) {

        if (t != null) {
            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getIcon(), 72, 72,
                    true);
            holder.setText(R.id.tv_fullname, t.getFullname())
                    .setText(R.id.tv_pid, "主营：" + t.getPid())
                    .setText(R.id.tv_tell, "电话：" + (CommFunAndroid.isNullOrEmpty(t.getTell()) ? t.getMobile() : t.getTell()))
            ;
        }

        /**
         * 拨打电话
         */
        holder.getView(R.id.ll_call).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String phone_text = holder.getViewText(R.id.tv_phone);


                if (mCollectionListener != null) {
                    mCollectionListener.onCall(t);
                }

            }
        });

        holder.getView(R.id.ll_cancel_collection).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mCollectionListener != null) {
                    mCollectionListener.onCancelCollection(t.getId());
                }

            }
        });

    }

    public interface CollectionListener {
        public void onCancelCollection(int id);

        public void onCall(StoreInfo storeInfo);
    }

    private CollectionListener mCollectionListener;

    public void setCollectionListener(CollectionListener collectionListener) {
        this.mCollectionListener = collectionListener;
    }

}
