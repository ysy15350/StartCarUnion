package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 车辆品牌gridview
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Call extends CommonAdapter<String> {


    public ListViewAdpater_Call(Context context, List<String> list) {
        super(context, list, R.layout.list_item_call);


    }

    @Override
    public void convert(ViewHolder holder, String t) {
        if (t != null) {

            if(t.startsWith("1")){
                holder.setVisibility_GONE(R.id.ll_tel).setVisibility_VISIBLE(R.id.ll_mobile).setText(R.id.tv_mobile,t);
            }
            else {
                holder.setVisibility_GONE(R.id.ll_mobile).setVisibility_VISIBLE(R.id.ll_tel).setText(R.id.tv_tell,t);
            }

        }

    }


}
