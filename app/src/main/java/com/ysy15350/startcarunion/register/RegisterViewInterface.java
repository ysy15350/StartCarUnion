package com.ysy15350.startcarunion.register;

import api.base.model.Response;

public interface RegisterViewInterface {

	/**
	 * 注册回调
	 * 
	 * @param response
	 */
	public void registerCallback(Response response);

	/**
	 * 上传头像回调
	 * @param response
	 */
	public void uppictureCallback(Response response);

	public void userLoginCallBack(Response response);

}
