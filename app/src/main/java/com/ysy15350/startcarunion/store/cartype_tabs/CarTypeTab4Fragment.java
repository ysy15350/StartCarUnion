package com.ysy15350.startcarunion.store.cartype_tabs;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.GridViewAdpater_CarType;
import com.ysy15350.startcarunion.business.StoreInfoListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.CarType;
import base.BaseData;
import base.mvp.MVPBaseFragment;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.JsonConvertor;

/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_car_type_tab)
public class CarTypeTab4Fragment extends MVPBaseFragment<CarTypeTabViewInterface, CarTypeTabPresenter>
        implements CarTypeTabViewInterface {


    @ViewInject(R.id.gv_car)
    private GridView gv_car;

    public CarTypeTab4Fragment() {
    }


    @Override
    public CarTypeTabPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new CarTypeTabPresenter(getActivity());
    }

    @Override
    public void initView() {
        super.initView();
        gv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item != null && item instanceof CarType) {
                    CarType carType = (CarType) item;
                    int id = carType.getId();
                    Intent intent = new Intent(mContext, StoreInfoListActivity.class);
                    intent.putExtra("cartype_id", id);
                    startActivity(intent);
                    showMsg(carType.getTitle());
                    //getActivity().finish();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        loadCarType();
    }

    private void loadCarType() {
        String carTypeJson = BaseData.getCache("carType" + 3);

        if (!CommFunAndroid.isNullOrEmpty(carTypeJson)) {
            List<CarType> list = JsonConvertor.fromJson(carTypeJson, new TypeToken<List<CarType>>() {
            }.getType());
            bindCarTypeGridView(list);
        } else {

            int pid = 0;

            try {

                String carTypeTopJson = BaseData.getCache("carTypeTop");

                if (!CommFunAndroid.isNullOrEmpty(carTypeTopJson)) {

                    List<CarType> list = JsonConvertor.fromJson(carTypeTopJson, new TypeToken<List<CarType>>() {
                    }.getType());

                    if (list != null && !list.isEmpty() && list.size() > 3) {
                        CarType carType = list.get(3);
                        if (carType != null) {
                            pid = carType.getId();
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (pid != 0) {
                CommFunMessage.showWaitDialog(getActivity(), "数据加载中...");
                mPresenter.getCarTypeList(pid);
            }

            bindCarTypeGridView(null);
        }
    }

    @Override
    public void bindCarType(Response response) {

        CommFunMessage.hideWaitDialog();
        // TODO Auto-generated method stub
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {

                mHolder.setText(R.id.tv_result, response.getResult().toString());

                BaseData.setCache("carType" + 3, response.getResultJson(), 3600);//缓存时间单位：秒


                List<CarType> list = response.getData(new TypeToken<List<CarType>>() {
                }.getType());

                bindCarTypeGridView(list);

            }
            showMsg(msg);
        }
    }

    private void bindCarTypeGridView(List<CarType> list) {

        try {
            if (list != null && !list.isEmpty()) {
                if (list.size() != gv_car.getCount()) {
                    mHolder.setVisibility_VISIBLE(R.id.gv_car).setVisibility_GONE(R.id.ll_nodata);
                    mAdapter = new GridViewAdpater_CarType(mContext, list);
                    gv_car.setAdapter(mAdapter);
                }

            } else {
                mHolder.setVisibility_GONE(R.id.gv_car).setVisibility_VISIBLE(R.id.ll_nodata);
                mAdapter = new GridViewAdpater_CarType(mContext, new ArrayList<CarType>());
                gv_car.setAdapter(mAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Event(value = R.id.ll_nodata)
    private void ll_nodataClick(View view) {
        loadCarType();
    }

    GridViewAdpater_CarType mAdapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mAdapter.destoryImageView();
        mAdapter = null;
        System.gc();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
