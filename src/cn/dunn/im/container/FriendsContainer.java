package cn.dunn.im.container;

import java.util.List;

import cn.dunn.im.model.User;

/**
 * 
 * @Title: FriendsContainer.java
 * @Package cn.dunn.im.container
 * @Description: 我的好友
 * @author TangTianXiong
 * @date 2015年6月9日 下午4:47:25
 */
public enum FriendsContainer {
	INSTANCE;
	private List<User> friends = null;

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<User> getFriends() {
		return friends;
	}

	public boolean isFriends(String friendId) {
		for (User user : friends) {
			if (user.getUserid().equals(friendId)) {
				return true;
			}
		}
		return false;
	}

}
