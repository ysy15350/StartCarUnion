package com.ysy15350.startcarunion.log;

import com.ysy15350.startcarunion.log.model.RequestLog;

import java.util.List;

public interface LogListViewInterface {
	/**
	 * 绑定我的收藏
	 * 
	 * @param response
	 */
	public void bindData(List<RequestLog> list);
}
