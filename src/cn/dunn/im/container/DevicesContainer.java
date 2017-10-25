package cn.dunn.im.container;

import java.util.List;

import cn.dunn.im.model.Device;

/**
 * 
 * @Title: FriendsContainer.java
 * @Package cn.dunn.im.container
 * @Description: 我的好友
 * @author TangTianXiong
 * @date 2015年6月9日 下午4:47:25
 */
public enum DevicesContainer {
	INSTANCE;
	private List<Device> devices = null;

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public boolean isDevices(String deviceId) {
		for (Device device : devices) {
			if (device.getDeviceid().equals(deviceId)) {
				return true;
			}
		}
		return false;
	}

}
