package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.model.Message;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 我的消息
 *
 * @author yangshiyou
 */
public class ListViewAdpater_MyMsg extends CommonAdapter<Message> {

    public ListViewAdpater_MyMsg(Context context, List<Message> list) {
        super(context, list, R.layout.list_item_my_msg);

    }

    @Override
    public void convert(ViewHolder holder, Message t) {
        if (t != null) {

            long create_time = t.getCreate_time();
            String createTime = CommFunAndroid.stampToDateStr(create_time + "", "yyyy/MM/dd HH:mm:ss");

            holder.setText(R.id.tv_fullName, t.getTitle())
                    .setText(R.id.tv_content, t.getContent())
                    .setText(R.id.tv_create_time, createTime)
            ;

        }

    }

}
