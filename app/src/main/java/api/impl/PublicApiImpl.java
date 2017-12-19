package api.impl;

import api.PublicApi;
import api.base.model.Config;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;

public class PublicApiImpl implements PublicApi {

    private String moduleName = "Public/";

    // private String mUrl = Config.getServer_url() + moduleName;

    @Override
    public void getToken(String className, ApiCallBack callBack) {

        IServer server = new Request();

        server.setMethodName(moduleName + "send_token");

        server.setParam("account", Config.getTokenUsername());
        server.setParam("password", Config.getTokenPassword());
        server.setParam("className", className);

        server.setApiCallBack(callBack);

        //server.setUseCache(true, 5);

        server.request();

    }

    @Override
    public void register(int icon, String mobile, String fullname, String password, String address, int type,
                         String verification_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "register");

        server.setParam("icon", icon);
        server.setParam("mobile", mobile);
        server.setParam("fullname", fullname);
        server.setParam("password", password);
        server.setParam("address", address);
        server.setParam("type", type);
        server.setParam("verification_code", verification_code);

        server.setApiCallBack(callBack);

        server.request();

    }

    @Override
    public void login(String account, String password, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "login");

        server.setParam("account", account);
        server.setParam("password", password);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void checkVersion(int version_code, ApiCallBack callBack) {


        IServer server = new Request();

        server.setMethodName(moduleName + "checkVersion");

        server.setParam("platform", 1);
        server.setParam("type", 1);
        server.setParam("vsersionCode", version_code);


        server.setApiCallBack(callBack);

        server.request();
    }


}
