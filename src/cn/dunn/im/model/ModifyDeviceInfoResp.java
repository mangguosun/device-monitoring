package cn.dunn.im.model;

import java.io.Serializable;

/**
 * 修改用户响应
 * 
 * @author Administrator
 *
 */
public class ModifyDeviceInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String note;
	private Device device;
	private Boolean success;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
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
