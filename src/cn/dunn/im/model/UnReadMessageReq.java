package cn.dunn.im.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 未读消息请求
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class UnReadMessageReq implements Serializable {
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
	 * 用户id
	 * 
	 * @return
	 */
	private String userid;
	/**
	 * 对方的id
	 * 
	 * @return
	 */
	private String another;
	/**
	 * 群组 or 好友
	 * 
	 * @return
	 */
	private String type;

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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
