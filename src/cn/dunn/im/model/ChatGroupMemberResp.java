package cn.dunn.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @Title: ChatGroupMemberResp.java
 * @Package cn.dunn.im.model
 * @Description: 响应获取群成员消息
 * @author TangTianXiong
 * @date 2015年6月9日 上午9:37:38
 */
@XmlRootElement
public class ChatGroupMemberResp implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 窗口ID
	 */
	private String windKey;
	/**
	 * 成员
	 */
	private List<User> member = new ArrayList<User>();

	@XmlElement
	public String getWindKey() {
		return windKey;
	}

	public void setWindKey(String windKey) {
		this.windKey = windKey;
	}

	@XmlElement
	public List<User> getMember() {
		return member;
	}

	public void setMember(List<User> member) {
		this.member = member;
	}

}
