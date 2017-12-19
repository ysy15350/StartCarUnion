package api.base.server;

import api.base.model.Config;
import api.base.model.Response;
import base.BaseData;

public abstract class ApiCallBack {


    public void onSuccess(boolean isCache, String data) {
    }

    public void onSuccess(boolean isCache, Response response) {

    }

    public void onFailed(String msg) {
    }
}
