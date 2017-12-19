package com.ysy15350.startcarunion.log;

import android.content.Context;

import com.ysy15350.startcarunion.log.dao.RequestLogDao;
import com.ysy15350.startcarunion.log.model.RequestLog;

import java.util.ArrayList;
import java.util.List;

import base.mvp.BasePresenter;

public class LogListPresenter extends BasePresenter<LogListViewInterface> {

    public LogListPresenter(Context context) {
        super(context);

    }

    RequestLogDao requestLogDao = RequestLogDao.getInstance();


    public void getMyCollection() {

        //noinspection Since15
        try {
            List<RequestLog> list = requestLogDao.getList();

            List<RequestLog> list_1 = new ArrayList<RequestLog>();
            for (int i = list.size(); i > 0; i--) {
                list_1.add(list.get(i - 1));
            }
            mView.bindData(list_1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
