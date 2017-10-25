package cn.dunn.im.model;

import java.io.Serializable;

/**
 * 修改用户信息请求
 * 
 * @author Administrator
 *
 */
public class ModifyDeviceInfoReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private Device device;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
