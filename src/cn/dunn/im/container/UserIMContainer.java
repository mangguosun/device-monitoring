package cn.dunn.im.container;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.Pane;
import cn.dunn.im.model.User;

public enum UserIMContainer {
	INSTANCE;
	private Map<String, User> friends = new HashMap<String, User>();
	private Map<String, Pane> friendComponent = new HashMap<String, Pane>();

	public Map<String, User> getFriends() {
		return friends;
	}

	public void setFriends(Map<String, User> friends) {
		this.friends = friends;
	}

	public Map<String, Pane> getFriendComponent() {
		return friendComponent;
	}

	public void setFriendComponent(Map<String, Pane> friendComponent) {
		this.friendComponent = friendComponent;
	}

}
