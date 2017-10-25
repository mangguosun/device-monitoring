package cn.dunn.im.model;

import java.io.Serializable;

/**
 * 修改用户信息请求
 * 
 * @author Administrator
 *
 */
public class ModifyUserInfoReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
