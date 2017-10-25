package cn.dunn.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = UUID.randomUUID().toString();
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 分组所属人
	 */
	private String pertain;

	private List<Device> devices = new ArrayList<Device>();

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@XmlElement
	public String getPertain() {
		return pertain;
	}

	public void setPertain(String pertain) {
		this.pertain = pertain;
	}

	@XmlElement
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

}
