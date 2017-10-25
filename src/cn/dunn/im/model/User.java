package cn.dunn.im.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable {
	/**
	 * 在线
	 */
	public static final String ONLINE = "online";
	/**
	 * 不在线
	 */
	public static final String OFFLINE = "Offline";

	private static final long serialVersionUID = 1L;
	private String userid = UUID.randomUUID().toString();
	private String username;
	private String password;
	private String autograph;
	private String nickname;
	private String headImg;// 头像路径
	private String headMD5;// 头像md5码
	private Long registerTime = new Date().getTime();

	// 非持久化
	private File head;// 头像
	private Boolean isOnline;// 是否在线
	private int unReadMessageCount;// 未读的消息数
	/**
	 * 群角色
	 */
	private String groupRole;

	/**
	 * 所在的群id
	 */
	private String chatGroupId;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@XmlElement
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@XmlElement
	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	@XmlElement
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	@XmlElement
	public String getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(String chatGroupId) {
		this.chatGroupId = chatGroupId;
	}

	public String getHeadMD5() {
		return headMD5;
	}

	public void setHeadMD5(String headMD5) {
		this.headMD5 = headMD5;
	}

	public File getHead() {
		return head;
	}

	public void setHead(File head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", password=" + password + ", autograph=" + autograph + "]";
	}

}
