package com.ysy15350.startcarunion.store;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_City;
import com.ysy15350.startcarunion.business.StoreInfoListActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.CityInfo;
import base.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/6.
 */

@ContentView(R.layout.activity_list)
public class CityListActivity extends MVPBaseListViewActivity<CityListViewInterface, CityListPresenter>
        implements CityListViewInterface {

    /**
     * 0:跳转商家列表；1：选择城市
     */
    public static int type = 0;

    @Override
    protected CityListPresenter createPresenter() {

        return new CityListPresenter(CityListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);

        setFormHead("地址选择");

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String cityInfo = BaseData.getCache("cityInfo");
        if (!CommFunAndroid.isNullOrEmpty(cityInfo)) {
            List<CityInfo> list = JsonConvertor.fromJson(cityInfo, new TypeToken<List<CityInfo>>() {
            }.getType());
            bindData(list);
        }

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        mPresenter.city_list(page, pageSize);
    }

    @Override
    public void city_listCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("cityInfo", response.getResultJson());
                    List<CityInfo> list = response.getData(new TypeToken<List<CityInfo>>() {
                    }.getType());
                    bindData(list);
                }
                xListView.stopRefresh();
                xListView.stopLoadMore();
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ListViewAdpater_City mAdapter;
    List<CityInfo> mList = new ArrayList<CityInfo>();


    public void bindData(List<CityInfo> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_City(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法
        //xListView.notify();

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }

    private CityInfo selectedCity;

    private View selectedCityView;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        View img_seleted = view.findViewById(R.id.img_seleted);
        if (selectedCityView != null) {
            selectedCityView.setVisibility(View.GONE);//把之前选择的城市不选择
            selectedCity.setSeleted(false);
        }
        CityInfo cityInfo = (CityInfo) parent.getItemAtPosition(position);

        cityInfo.setSeleted(true);
        img_seleted.setVisibility(View.VISIBLE);

        selectedCityView = img_seleted;
        selectedCity = cityInfo;

        onSeletedCity();

    }

    private void onSeletedCity() {
        if (selectedCity != null) {
            int city_id = selectedCity.getId();
            String city_name = selectedCity.getTitle();
            switch (type) {
                case 0:
                    Intent store_intent = new Intent(this, StoreInfoListActivity.class);
                    store_intent.putExtra("city_id", city_id);
                    startActivity(store_intent);
                    break;
                case 1:
                    Intent intent = getIntent();
                    intent.putExtra("city_id", city_id);
                    intent.putExtra("city_name", city_name);
                    setResult(1, intent);
                    this.finish();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(mAdapter!=null)
                mAdapter.destoryImageView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}