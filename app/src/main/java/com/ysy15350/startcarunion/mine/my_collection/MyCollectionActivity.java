package com.ysy15350.startcarunion.mine.my_collection;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_MyCollection;
import com.ysy15350.startcarunion.business.StoreInfoDetailActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.StoreInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.CommFunMessage;
import custom_view.popupwindow.CallPopupWindow;

@ContentView(R.layout.activity_list)
public class MyCollectionActivity extends MVPBaseListViewActivity<MyCollectionViewInterface, MyCollectionPresenter>
        implements MyCollectionViewInterface {

    @Override
    protected MyCollectionPresenter createPresenter() {

        return new MyCollectionPresenter(MyCollectionActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(13));

        setFormHead("我的收藏");

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        CommFunMessage.showWaitDialog(this,"数据加载中...");
        mPresenter.coll_list(page, pageSize);
    }

    ListViewAdpater_MyCollection mAdapter;
    List<StoreInfo> mList = new ArrayList<StoreInfo>();

    @Override
    public void coll_listCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    List<StoreInfo> list = response.getData(new TypeToken<List<StoreInfo>>() {
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

    public void bindData(List<StoreInfo> list) {
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
        mAdapter = new ListViewAdpater_MyCollection(this, mList);

        mAdapter.setCollectionListener(new ListViewAdpater_MyCollection.CollectionListener() {
            @Override
            public void onCancelCollection(int id) {
                mPresenter.collection(id);
            }

            @Override
            public void onCall(StoreInfo storeInfo) {
                final int uid = storeInfo.getId();

                List<String> phones = new ArrayList<>();

                String mobile = storeInfo.getMobile();
                if (!CommFunAndroid.isNullOrEmpty(mobile))
                    phones.add(mobile);

                String tell = storeInfo.getTell();


                if (!CommFunAndroid.isNullOrEmpty(tell)) {

                    String[] phonesArr = tell.split("/");

                    if (phonesArr != null && phonesArr.length > 0) {
                        for (String phone :
                                phonesArr) {
                            phones.add(phone);
                        }
                    }
                }

                if (phones == null || phones.size() == 0) {
                    return;
                }


                CallPopupWindow popupWindow = new CallPopupWindow(MyCollectionActivity.this, phones);

                popupWindow.setPopListener(new CallPopupWindow.PopListener() {
                    @Override
                    public void onCallMobile(String mobile) {
                        mPresenter.make_tell(uid, mobile);
                        //showMsg(mobile);
                        callPhoneCheckPermission(MyCollectionActivity.this, mobile);
                    }


                    @Override
                    public void onCancelClick() {

                    }
                });
                popupWindow.showPopupWindow(mContentView);
            }
        });

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }

    @Override
    public void make_tellCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void collectionCallback(boolean isCache, Response response) {
        try {

            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    page = 1;
                    initData(page, pageSize);
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        try {
            StoreInfo storeInfo = (StoreInfo) parent.getItemAtPosition(position);
            if (storeInfo != null) {
                Intent intent = new Intent(this, StoreInfoDetailActivity.class);
                intent.putExtra("id", storeInfo.getId());
                intent.putExtra("fullname", storeInfo.getFullname());
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
