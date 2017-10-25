package cn.dunn.im.model;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @Title: ChatGroup.java
 * @Package cn.dunn.im.model
 * @Description: 聊天群组
 * @author TangTianXiong
 * @date 2015年6月6日 下午2:41:49
 */
@XmlRootElement
public class ChatGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Object ROLE_GROUP_CREATOR = "creator";
	public static Object ROLE_GROUP_ADMIN = "admin";
	private String id = UUID.randomUUID().toString();
	/**
	 * 群组名称
	 */
	private String chatGroupName;
	/**
	 * 群组主题
	 */
	private String theme;
	/**
	 * 群组头像
	 */
	private String headImg;
	/**
	 * 用户在该群组中的未读消息条数
	 */
	private int unReadMessageCount;

	private File head;

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getChatGroupName() {
		return chatGroupName;
	}

	public void setChatGroupName(String chatGroupName) {
		this.chatGroupName = chatGroupName;
	}

	@XmlElement
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@XmlElement
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@XmlElement
	public int getUnReadMessageCount() {
		return unReadMessageCount;
	}

	public void setUnReadMessageCount(int unReadMessageCount) {
		this.unReadMessageCount = unReadMessageCount;
	}

	public File getHead() {
		return head;
	}

	public void setHead(File head) {
		this.head = head;
	}

}
