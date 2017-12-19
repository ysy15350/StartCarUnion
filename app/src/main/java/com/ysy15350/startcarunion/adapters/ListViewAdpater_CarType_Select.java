package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.model.CarType;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 车牌品牌listview
 *
 * @author yangshiyou
 */
public class ListViewAdpater_CarType_Select extends CommonAdapter<CarType> {


    public ListViewAdpater_CarType_Select(Context context, List<CarType> list) {
        super(context, list, R.layout.list_item_car_type_select);


    }

    @Override
    public void convert(ViewHolder holder, CarType t) {
        if (t != null) {

            holder.setImageURL(R.id.img_head,
                    "http://www.ysy15350.com:80/uploadFiles/uploadImgs/20170719/f0c2db57c552470ca24965108963c964.jpg",
                    true);

        }

    }

}
