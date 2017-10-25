package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 传输消息
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TYPE_CHAT = "chat";
	public static final String TYPE_GROUP = "chatgroup";
	private Long id;
	private String from;
	private String to;
	private String type;
	private String body;
	private Long createTime;
	/**
	 * 消息发送人
	 */
	private User user;

	@XmlElement
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@XmlElement
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@XmlElement
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@XmlElement
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", from=" + from + ", to=" + to + ", type=" + type + ", body=" + body + ", createTime=" + createTime + "]";
	}

}
