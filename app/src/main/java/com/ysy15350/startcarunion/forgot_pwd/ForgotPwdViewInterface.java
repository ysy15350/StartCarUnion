package com.ysy15350.startcarunion.forgot_pwd;

import api.base.model.Response;

public interface ForgotPwdViewInterface {

    public void send_mobile_codeCallback(boolean isCache, Response response);

    public void save_passwordCallback(boolean isCache, Response response);

}
