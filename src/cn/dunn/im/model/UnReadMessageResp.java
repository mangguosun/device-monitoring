package cn.dunn.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 未读消息响应
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class UnReadMessageResp implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 好友
	 */
	public static final String CHAT = "chat";
	/**
	 * 群组
	 */
	public static final String CHAT_GROUP = "chatgroup";

	/**
	 * 窗口key
	 */
	private String windKey;
	/**
	 * 群组 or 好友
	 * 
	 * @return
	 */
	private String type;

	/**
	 * 未读的消息
	 */
	private List<Message> messages = new ArrayList<Message>();

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

	@XmlElement
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
