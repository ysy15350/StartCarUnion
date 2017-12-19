package com.ysy15350.startcarunion.mine.my_comment;

import api.base.model.Response;

public interface MyCommentViewInterface {

	/**
	 * 我的评论
	 * @param response
	 */
	public void comment_listCallback(boolean isCache, Response response);
}
