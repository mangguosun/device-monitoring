package cn.dunn.im.model;

import java.io.Serializable;

/**
 * 修改用户响应
 * 
 * @author Administrator
 *
 */
public class ModifyUserInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String note;
	private User user;
	private Boolean success;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
