package cn.dunn.im.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 我的好友列表
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
public class MyFriendGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<FriendGroup> friendGroup;

	public MyFriendGroup() {
	}

	public MyFriendGroup(List<FriendGroup> friendGroup) {
		this.friendGroup = friendGroup;
	}

	@XmlElement
	public List<FriendGroup> getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(List<FriendGroup> friendGroup) {
		this.friendGroup = friendGroup;
	}

}
