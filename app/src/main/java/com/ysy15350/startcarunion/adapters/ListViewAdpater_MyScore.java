package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.Date;
import java.util.List;

import api.model.MyScore;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 我的积分
 *
 * @author yangshiyou
 */
public class ListViewAdpater_MyScore extends CommonAdapter<MyScore> {

    public ListViewAdpater_MyScore(Context context, List<MyScore> list) {
        super(context, list, R.layout.list_item_my_score);

    }

    @Override
    public void convert(final ViewHolder holder, MyScore t) {

        if (t != null) {
            long create_time = t.getCreate_time();
            Date createTime = CommFunAndroid.stampToDate(t.getCreate_time() + "");
            holder.setText(R.id.tv_day, CommFunAndroid.toDateString(createTime, "dd"))
                    .setText(R.id.tv_month, CommFunAndroid.getMonthStr(createTime))
                    .setText(R.id.tv_number, "+ " + t.getNumber())
                    .setText(R.id.tv_score, "我的积分：" + t.getPoint())
            ;
        }

    }

}
