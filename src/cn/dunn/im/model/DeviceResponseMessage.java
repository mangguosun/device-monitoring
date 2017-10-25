package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceResponseMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String reason;
	private String msg;
	private Boolean success;
	private Device device;

	public DeviceResponseMessage() {
	}

	public DeviceResponseMessage(Boolean success) {
		this.success = success;
	}
	
	@XmlElement
	public String getMsg() {
		return msg;
	}

	@XmlElement
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@XmlElement
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@XmlElement
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
