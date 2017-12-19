package com.ysy15350.startcarunion.mine.my_shop;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.startcarunion.R;
import com.ysy15350.startcarunion.adapters.ListViewAdpater_Tell;
import com.ysy15350.startcarunion.dialog.AddContactNumberDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import base.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.JsonConvertor;

@ContentView(R.layout.activity_list)
public class TellListActivity extends MVPBaseListViewActivity<TellListViewInterface, TellListPresenter>
        implements TellListViewInterface {

    @Override
    protected TellListPresenter createPresenter() {

        return new TellListPresenter(TellListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(0));
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);

        Intent intent = getIntent();
        if (intent != null) {
            int type = intent.getIntExtra("type", -1);
            mType = type;
            switch (type) {
                case 1://电话
                    setFormHead("联系电话");
                    break;
                case 2://手机号
                    setFormHead("手机号");
                    break;
                default:
                    finish();
                    break;
            }
        }


        setMenuText("添加");
    }

    int mType = 1;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        BaseData.setCache("tellList" + mType, result);
        String tellListJson = BaseData.getCache("tellList" + mType);
        if (!CommFunAndroid.isNullOrEmpty(tellListJson)) {
            List<String> list = JsonConvertor.fromJson(tellListJson, new TypeToken<List<String>>() {
            }.getType());

            bindTellList(list);

        }

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        mPresenter.store_tell(mType);
    }

    ListViewAdpater_Tell mAdapter;
    List<String> mList = new ArrayList<String>();


    @Override
    public void store_tellCallback(boolean isCache, Response response) {


        List<String> list = new ArrayList<String>();
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    String result = response.getResultJson();
                    BaseData.setCache("tellList" + mType, result);
                    list = response.getData(new TypeToken<List<String>>() {
                    }.getType());
                } else {
                    xListView.stopRefresh();
                    xListView.stopLoadMore();

                    showMsg(msg);
                }
            }


            if (page == 1) {
                mList = new ArrayList<String>();
            } else {
//            if (list == null || list.isEmpty())
//                showMsg("没有更多了");
            }

            bindTellList(list);

            if (list != null || !list.isEmpty()) {
                page++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void bindTellList(List<String> list) {

        try {
            if (list != null)
                mList.addAll(list);

            mAdapter = new ListViewAdpater_Tell(this, mList);

            mAdapter.setListener(new ListViewAdpater_Tell.AdapterListener() {
                @Override
                public void editTell(String tell) {
                    addOrUpdateContactNumDialog(tell, 2);
                }

                @Override
                public void deleteTell(String tell) {
                    mPresenter.add_store_tell(tell, 2, mType);
                }
            });

            bindListView(mAdapter);// 调用父类绑定数据方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加
     *
     * @param isCache
     * @param response
     */
    @Override
    public void add_store_tellCallback(boolean isCache, Response response) {
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

    /**
     * 添加（菜单）
     *
     * @param view
     */
    @Event(value = R.id.tv_menu)
    private void tv_menuClick(View view) {
        addOrUpdateContactNumDialog("", 1);
    }

    private void addOrUpdateContactNumDialog(final String tell, int type) {
        AddContactNumberDialog dialog = new AddContactNumberDialog(this, tell, type);
        dialog.setDialogListener(new AddContactNumberDialog.DialogListener() {

            @Override
            public void onOkClick(String tell_add, int type) {
                switch (type) {
                    case 1:
                        mPresenter.add_store_tell(tell_add, 1, mType);
                        break;
                    case 2:
                        mPresenter.update_store_tell(tell, tell_add, mType);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onCancelClick() {

            }
        });
        dialog.show();
    }

    @Override
    protected void back() {

        String phoneStr = "";
        if (mList != null && !mList.isEmpty()) {
            for (String phone :
                    mList) {
                phoneStr = phoneStr + phone + "\n";
            }

            Intent intent = getIntent();
            intent.putExtra("phoneStr", phoneStr.substring(0, phoneStr.length() - 1));
            setResult(RESULT_OK, intent);
        } else {
            Intent intent = getIntent();
            intent.putExtra("phoneStr", "");
            setResult(RESULT_OK, intent);
        }

        super.back();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mAdapter != null)
                mAdapter.destoryImageView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
