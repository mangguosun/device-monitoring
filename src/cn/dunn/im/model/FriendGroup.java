package cn.dunn.im.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FriendGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = UUID.randomUUID().toString();
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 分组所属人
	 */
	private String pertain;

	private List<User> friends = new ArrayList<User>();

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@XmlElement
	public String getPertain() {
		return pertain;
	}

	public void setPertain(String pertain) {
		this.pertain = pertain;
	}

	@XmlElement
	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

}
