package api.impl;

import java.io.File;

import api.MemberApi;
import api.base.server.ApiCallBack;
import api.base.server.IServer;
import api.base.server.Request;
import base.BaseData;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class MemberApiImpl implements MemberApi {

    private String moduleName = "Member/";

    @Override
    public void uppicture(File file, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "uppicture");

        server.setParam("filename", file);//filename   upload.png

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void user_info(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "user_info");

        server.setParam("uid", BaseData.getInstance().getUid());

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_info(String field, String value, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_info");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("field", field);
        server.setParam("value", value);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_mobile(String password, String mobile, String mobile_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_mobile");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("mobile_code", mobile_code);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_password(String password, String mobile, String mobile_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_password");

        //server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("mobile_code", mobile_code);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_info(int uid, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_info");

        server.setParam("uid", uid);

        server.setParam("cid", BaseData.getInstance().getUid());

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_store_info(String ad_img, String tent, String tell, int city_id, String address, String qq, String email, String brand, String atlas, String type_info, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_store_info");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("ad_img", ad_img);
        server.setParam("tent", tent);
        //server.setParam("tell", tell);
        server.setParam("city_id", city_id);
        server.setParam("address", address);
        server.setParam("qq", qq);
        server.setParam("email", email);
        server.setParam("brand", brand);
        server.setParam("atlas", atlas);
        server.setParam("type_info", type_info);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_tell(int type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_tell");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void add_store_tell(String tell, int type,int mobile_type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "add_store_tell");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("tell", tell);
        server.setParam("type", type);
        server.setParam("mobile_type", mobile_type);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void coll_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "coll_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void comment_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "comment_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void sign_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "sign_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void dial_record(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "dial_record");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void visitors_list(int type, int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "visitors_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void message_list(int page, int limit, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "message_list");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", limit);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void send_mobile_code(String password, String mobile, String type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "send_mobile_code");

        server.setParam("sid", BaseData.getInstance().getUid());
        if (!CommFunAndroid.isNullOrEmpty(password))
            server.setParam("password", password);
        server.setParam("mobile", mobile);
        server.setParam("type", type);

        server.setApiCallBack(callBack);

        server.request();
    }
}
