package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.MyCall;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 留言列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_MyCall extends CommonAdapter<MyCall> {

    public ListViewAdpater_MyCall(Context context, List<MyCall> list) {
        super(context, list, R.layout.list_item_my_comment);

    }

    @Override
    public void convert(ViewHolder holder, MyCall t) {
        if (t != null) {

            long create_time = t.getCreate_time();
            String createTime = CommFunAndroid.stampToDateStr(create_time + "", "yyyy/MM/dd HH:mm:ss");

            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getLitpic(), 72, 72,
                    true)
                    .setText(R.id.tv_fullname, t.getNickname())
                    .setText(R.id.tv_content, t.getContent())
                    .setText(R.id.tv_create_time, createTime)
            ;

        }

    }

}
