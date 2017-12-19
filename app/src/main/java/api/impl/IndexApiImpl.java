package api.impl;

import api.IndexApi;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;
import base.BaseData;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class IndexApiImpl implements IndexApi {

    private String moduleName = "index/";

    @Override
    public void home_banaer(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "home_banaer");


        server.setApiCallBack(callBack);

        //server.setUseCache(true, 60);

        server.request();
    }

    @Override
    public void qd_welcome(ApiCallBack callBack) {

        IServer server = new Request();

        server.setMethodName(moduleName + "qd_welcome");

        server.setApiCallBack(callBack);

        //server.setUseCache(true, 60);

        server.request();

    }

    @Override
    public void store_sign(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_sign");

        server.setParam("uid", BaseData.getInstance().getUid());

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void guarantee_code(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "guarantee_code");

        // server.setParam("uid", BaseData.getUid());

        server.setApiCallBack(callBack);

        server.request();
    }
}
