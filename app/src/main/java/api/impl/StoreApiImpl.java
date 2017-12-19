package api.impl;

import api.StoreApi;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;
import base.BaseData;
import common.CommFunAndroid;

public class StoreApiImpl implements StoreApi {

    private String moduleName = "Store/";

    // private String mUrl = Config.getServer_url() + moduleName;


    @Override
    public void store_list(int flag, int pid, int city_id, String keywords, int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_list");

        if (flag != -1) {
            server.setParam("flag", flag);
        }
        if (pid != -1)
            server.setParam("pid", pid);
        if (city_id != -1)
            server.setParam("city_id", city_id);
        if (!CommFunAndroid.isNullOrEmpty(keywords))
            server.setParam("keywords", keywords);
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void collection(int sid, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "collection");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_comment(int sid, int type, String content, int pid, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_comment");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);
        server.setParam("type", type);
        server.setParam("content", content);
        if (pid != 0)
            server.setParam("pid", pid);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void comment_list(int sid, int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "comment_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        if (sid != 0)
            server.setParam("sid", sid);
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void make_tell(int sid, String tell, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "make_tell");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);
        server.setParam("tell", tell);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_browse(int sid, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_browse");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void city_list(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "city_list");


        server.setApiCallBack(callBack);

        server.setCacheTime(3600);

        server.request();
    }

    @Override
    public void contacts(String tell, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "contacts");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("tell", tell);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void contactslist(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "contactslist");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void comment_reply(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "comment_reply");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void comment_view(int sid, ApiCallBack callBack) {

        IServer server = new Request();

        server.setMethodName(moduleName + "comment_view");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);

        server.setApiCallBack(callBack);

        server.request();
    }
}
