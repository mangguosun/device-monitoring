package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotifyMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 通知类型:上线通知 body为user
	 */

	public static final String STATUS_ONLINE = "online";

	public static final String STATUS_OFFLINE = "Offline";
	/**
	 * 好友修改信息
	 */
	public static final String STATUS_MODIFY = "modifyInfo";

	/**
	 * 参照message类型
	 */
	private String type;
	private String status;
	private User body;

	public NotifyMessage() {
	}

	public NotifyMessage(String status) {
		this.status = status;
	}

	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement
	public User getBody() {
		return body;
	}

	public void setBody(User body) {
		this.body = body;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
