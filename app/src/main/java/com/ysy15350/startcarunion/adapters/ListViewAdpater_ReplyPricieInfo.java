package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.model.AskPricieInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

public class ListViewAdpater_ReplyPricieInfo extends CommonAdapter<AskPricieInfo.Reply> {

    public ListViewAdpater_ReplyPricieInfo(Context context, List<AskPricieInfo.Reply> list) {
        super(context, list, R.layout.list_item_reply_price);

    }

    @Override
    public void convert(final ViewHolder holder, AskPricieInfo.Reply t) {

        if (t != null) {

            long create_time = t.getCreate_time();
            String createTime = CommFunAndroid.stampToDateStr(create_time + "", "yyyy/MM/dd");

            holder.setText(R.id.tv_content, t.getContent())
                    .setText(R.id.tv_create_time, createTime)
            ;
        }


    }

}
