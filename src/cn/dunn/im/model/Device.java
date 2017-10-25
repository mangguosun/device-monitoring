package cn.dunn.im.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Device implements Serializable {
	/**
	 * 在线
	 */
	public static final String ONLINE = "online";
	/**
	 * 不在线
	 */
	public static final String OFFLINE = "Offline";

	private static final long serialVersionUID = 1L;
	private String deviceid = UUID.randomUUID().toString();
	private String devicename;
	private String password;
	private String autograph;
	private String deviceip;
	private String devicemac;	
	private Long registerTime = new Date().getTime();

	// 非持久化
	private Boolean isOnline;// 是否在线
	private int unReadMessageCount;// 未读的消息数
	/**
	 * 群角色
	 */
	private String groupRole;

	public Device() {
	}

	public Device(String deviceip,String devicename,String autograph,String password,String devicemac) {
		this.deviceip = deviceip;
		this.devicename = devicename;
		this.autograph = autograph;
		this.password = password;
		this.devicemac = devicemac;
	}

	@XmlElement
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	@XmlElement
	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	@XmlElement
	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	@XmlElement
	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
	@XmlElement
	public String getDevicemac() {
		return devicemac;
	}

	public void setDevicemac(String devicemac) {
		this.devicemac = devicemac;
	}
	@XmlElement
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	@XmlElement
	public int getUnReadMessageCount() {
		return unReadMessageCount;
	}

	public void setUnReadMessageCount(int unReadMessageCount) {
		this.unReadMessageCount = unReadMessageCount;
	}

	@XmlElement
	public String getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(String groupRole) {
		this.groupRole = groupRole;
	}


	@Override
	public String toString() {
		return "Device [deviceid=" + deviceid + ", devicename=" + devicename + ", registerTime=" + registerTime + ", deviceip=" + deviceip + ", devicemac=" + devicemac + ", password=" + password + ", autograph=" + autograph + "]";
	}

}
