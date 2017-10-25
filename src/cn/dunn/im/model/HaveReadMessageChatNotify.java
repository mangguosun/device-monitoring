package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 已读消息通知
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class HaveReadMessageChatNotify implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 好友消息
	 */
	public static final String CHAT = "chat";
	/**
	 * 群组消息
	 */
	public static final String CHAT_GROUP = "chatgroup";
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 好友id或者群id
	 */
	private String another;
	/**
	 * 最后阅读的时间
	 */
	private Long readTime;

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@XmlElement
	public String getAnother() {
		return another;
	}

	public void setAnother(String another) {
		this.another = another;
	}

	@XmlElement
	public Long getReadTime() {
		return readTime;
	}

	public void setReadTime(Long readTime) {
		this.readTime = readTime;
	}

}
