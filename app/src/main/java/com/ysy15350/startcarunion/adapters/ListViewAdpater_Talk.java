package com.ysy15350.startcarunion.adapters;

import android.content.Context;
import android.widget.LinearLayout;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.MyComment;
import base.BaseData;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 留言列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Talk extends CommonAdapter<MyComment> {

    public ListViewAdpater_Talk(Context context, List<MyComment> list) {
        super(context, list, R.layout.list_item_talk);

    }

    @Override
    public void convert(ViewHolder holder, MyComment t) {
        if (t != null) {
            LinearLayout ll_business = holder.getView(R.id.ll_business);
            LinearLayout ll_mine = holder.getView(R.id.ll_mine);

            int uid = t.getUid();
            int sid = t.getSid();

            long create_time = t.getCreate_time();
            String createTime = CommFunAndroid.stampToDateStr(create_time + "", "yyyy/MM/dd HH:mm:ss");

            int myUid = CommFunAndroid.toInt32(BaseData.getInstance().getUid(), -1);
            if (myUid != uid) {//如果自己id和uid不等，说明是商家回复的，显示左边
                holder.setVisibility_VISIBLE(R.id.ll_business).setVisibility_GONE(R.id.ll_mine);
                holder.setText(R.id.tv_content_business, t.getContent())
                        .setText(R.id.tv_create_time_business, createTime)
                        .setImageURL(R.id.img_head_business, Config.getServer() + t.getUid_icon(), true)
                ;
            } else {
                holder.setVisibility_GONE(R.id.ll_business).setVisibility_VISIBLE(R.id.ll_mine);
                holder.setText(R.id.tv_content_mine, t.getContent())
                        .setText(R.id.tv_create_time_mine, createTime)
                        .setImageURL(R.id.img_head_mine, Config.getServer() + t.getUid_icon(), true)
                ;
            }

        }

    }

}
