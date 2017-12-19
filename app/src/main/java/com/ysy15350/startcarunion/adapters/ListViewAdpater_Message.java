package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.Message;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 留言列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Message extends CommonAdapter<Message> {

    public ListViewAdpater_Message(Context context, List<Message> list) {
        super(context, list, R.layout.list_item_message);

    }

    @Override
    public void convert(ViewHolder holder, Message t) {
        if (t != null) {

//            tv_new

            int view = t.getView();//0:无新消息；1：有新消息

            holder.setImageURL(R.id.img_head,
                    Config.getServer() + t.getIcon(), 85, 85,
                    true)
                    .setText(R.id.tv_fullName, t.getFullname())
                    .setText(R.id.tv_content, t.getContent())
            ;

            if (view == 1) {
                holder.setVisibility_GONE(R.id.tv_new);
            } else {
                holder.setVisibility_VISIBLE(R.id.tv_new);
            }

        }

    }

}
