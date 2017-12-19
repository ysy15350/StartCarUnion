package com.ysy15350.startcarunion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.model.AskPricieInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

public class ListViewAdpater_AskPricieInfo extends CommonAdapter<AskPricieInfo> {

    public ListViewAdpater_AskPricieInfo(Context context, List<AskPricieInfo> list) {
        super(context, list, R.layout.list_item_ask_price);

    }

    ListViewAdpater_ReplyPricieInfo listViewAdpater_replyPricieInfo;

    @Override
    public void convert(final ViewHolder holder, AskPricieInfo t) {

        if (t != null) {


            List<AskPricieInfo.Reply> replyList = t.getReply();
            if (replyList != null && !replyList.isEmpty()) {

                LinearLayout ll_reply = holder.getView(R.id.ll_reply);

                for (AskPricieInfo.Reply replay :
                        replyList) {


                    View ll_reply_item = LayoutInflater.from(mContext).inflate(R.layout.list_item_reply_price, null);

                    TextView tv_content = (TextView) ll_reply_item.findViewById(R.id.tv_content);
                    tv_content.setText(replay.getContent());


                    ll_reply.addView(ll_reply_item);
                }


            }

            long create_time = t.getCreate_time();
            String createTime = CommFunAndroid.stampToDateStr(create_time + "", "yyyy/MM/dd");

            holder.setText(R.id.tv_content, t.getContent())
                    .setText(R.id.tv_create_time, createTime)
            ;
        }


    }

}
