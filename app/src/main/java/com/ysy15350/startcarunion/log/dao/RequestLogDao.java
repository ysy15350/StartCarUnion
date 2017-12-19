package com.ysy15350.startcarunion.log.dao;

import com.ysy15350.startcarunion.log.model.RequestLog;

import java.util.Collection;
import java.util.List;

import common.database.DbUtilsXutils3;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class RequestLogDao extends DbUtilsXutils3<RequestLog> {

    private static RequestLogDao requestLogDao;

    private RequestLogDao() {

    }

    public static RequestLogDao getInstance() {
        if (requestLogDao == null) {
            requestLogDao = new RequestLogDao();
            init();
        }
        return requestLogDao;
    }

    @Override
    public int saveOrUpdate(RequestLog requestLog) throws Exception {
        db.saveOrUpdate(requestLog);
        return 1;
    }

    @Override
    public int insert(RequestLog requestLog) throws Exception {
        db.save(requestLog);
        return 1;
    }

    @Override
    public int update(RequestLog requestLog) throws Exception {
        db.saveOrUpdate(requestLog);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        return 0;
    }

    @Override
    public int delete(int id) throws Exception {
        db.deleteById(RequestLog.class, id);
        return 1;
    }

    @Override
    public int delete(Collection<RequestLog> list) throws Exception {
        return 0;
    }

    @Override
    public RequestLog getEntity(int id) throws Exception {
        RequestLog requestLog = db.findById(RequestLog.class, id);
        return requestLog;
    }

    @Override
    public List<RequestLog> getList() throws Exception {
        List<RequestLog> list = db.findAll(RequestLog.class);
        return list;
    }
}
