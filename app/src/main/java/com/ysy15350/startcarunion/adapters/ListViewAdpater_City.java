package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.model.CityInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 留言列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_City extends CommonAdapter<CityInfo> {

    public ListViewAdpater_City(Context context, List<CityInfo> list) {
        super(context, list, R.layout.list_item_city);

    }

    @Override
    public void convert(ViewHolder holder, CityInfo t) {
        if (t != null) {
            holder.setText(R.id.tv_title, t.getTitle());
            boolean isSeleted = t.isSeleted();
            if (isSeleted) {
                holder.setVisibility_VISIBLE(R.id.img_seleted);
            } else
                holder.setVisibility_GONE(R.id.img_seleted);
        }

    }

}
