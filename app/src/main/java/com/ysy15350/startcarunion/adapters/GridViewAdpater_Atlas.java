package com.ysy15350.startcarunion.adapters;

import android.content.Context;

import com.ysy15350.startcarunion.R;

import java.util.List;

import api.base.model.Config;
import api.model.CarType;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFun;

/**
 * 车辆品牌gridview
 *
 * @author yangshiyou
 */
public class GridViewAdpater_Atlas extends CommonAdapter<CarType> {


    public GridViewAdpater_Atlas(Context context, List<CarType> list) {
        super(context, list, R.layout.grid_item_atlas);


    }

    @Override
    public void convert(ViewHolder holder, CarType t) {

        try {
            if (t != null) {

                if (CommFun.isNullOrEmpty(t.getTitle())) {
                    holder.setVisibility_VISIBLE(R.id.ll_upload_atlas).setVisibility_GONE(R.id.img_car_logo);
                } else {
                    holder
                            .setVisibility_GONE(R.id.ll_upload_atlas).setVisibility_VISIBLE(R.id.img_car_logo)
                            .setImageURL(R.id.img_car_logo, Config.getServer() + t.getIcon())
                            .setText(R.id.tv_title, t.getTitle());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
