package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceRequestMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userid;
	private String deviceid;
	private String deviceip;
	private String devicename;
	private String autograph;
	private String password;
	private String devicemac;
	// @XmlElement
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	// @XmlElement
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	// @XmlElement
	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
	// @XmlElement
	public String getDevicemac() {
		return devicemac;
	}

	public void setDevicemac(String devicemac) {
		this.devicemac = devicemac;
	}

	// @XmlElement
	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	// @XmlElement
	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	// @XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "DeviceRequestMessage [userid=" + userid + ", devicename=" + devicename + ", autograph=" + autograph + ", password=" + password + ", deviceid=" + deviceid + ", devicemac=" + devicemac + ", deviceip=" + deviceip + "]";
	}
}
