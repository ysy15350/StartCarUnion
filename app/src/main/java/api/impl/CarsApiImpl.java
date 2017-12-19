package api.impl;

import api.CarsApi;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;
import base.BaseData;

public class CarsApiImpl implements CarsApi {

    private String moduleName = "Cars/";

    // private String mUrl = Config.getServer_url() + moduleName;

    @Override
    public void pro_type(int pid, ApiCallBack callBack) {

        IServer server = new Request();

        server.setMethodName(moduleName + "pro_type");

        server.setParam("pid", pid);

        server.setApiCallBack(callBack);

        server.setCacheTime(3600);

        server.request();

    }

    @Override
    public void type_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "type_list");

        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        //server.setUseCache(true, 5);

        server.request();
    }

    @Override
    public void inquiry_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "inquiry_list");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        //server.setUseCache(true, 5);

        server.request();
    }

    @Override
    public void inquiry_info(int qid, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "inquiry_info");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("qid", qid);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void add_inquiry(int pid, String atlas, String content, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "add_inquiry");

        if (pid != 0)
            server.setParam("pid", pid);
        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("atlas", atlas);
        server.setParam("content", content);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void use_inquiry_info(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "use_inquiry_info");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }


}
