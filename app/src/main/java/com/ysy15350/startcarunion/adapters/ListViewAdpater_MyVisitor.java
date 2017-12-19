package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.StoreInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 我的访客
 *
 * @author yangshiyou
 */
public class ListViewAdpater_MyVisitor extends CommonAdapter<StoreInfo> {

    public ListViewAdpater_MyVisitor(Context context, List<StoreInfo> list) {
        super(context, list, R.layout.list_item_my_visitor);

    }

    @Override
    public void convert(final ViewHolder holder, StoreInfo t) {
        if (t != null) {

            String date = t.getDate();
            holder.setVisibility_GONE(R.id.ll_top);
            if (!CommFunAndroid.isNullOrEmpty(date)) {
                holder.setVisibility_VISIBLE(R.id.ll_top).setText(R.id.tv_date, t.getDate())
                        .setText(R.id.tv_count, "(" + t.getCount() + "人)");
            }

            String update_time = CommFunAndroid.stampToDateStr(t.getUpdate_time() + "", "HH:mm:ss");

            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getLitpic(), 72, 72,
                    true);
            holder.setText(R.id.tv_fullName, t.getFullname()).setText(R.id.tv_update_time, update_time);
        }


    }

}
