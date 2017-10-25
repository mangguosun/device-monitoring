package cn.dunn.im.container;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.Pane;
import cn.dunn.im.model.Device;

public enum DeviceIMContainer {
	INSTANCE;
	private Map<String, Device> devices = new HashMap<String, Device>();
	private Map<String, Pane> deviceComponent = new HashMap<String, Pane>();

	public Map<String, Device> getDevices() {
		return devices;
	}

	public void setDevices(Map<String, Device> devices) {
		this.devices = devices;
	}

	public Map<String, Pane> getDeviceComponent() {
		return deviceComponent;
	}

	public void setDeviceComponent(Map<String, Pane> deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

}
