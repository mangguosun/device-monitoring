package cn.dunn.im.container;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ConnectContainer {
	INSTANCE;
	private Map<String, Channel> onLineUserConnect = new HashMap<String, Channel>();

	private Map<String, Channel> markChannelConnect = new HashMap<String, Channel>();

	private Map<String, String> markMapUsername = new HashMap<String, String>();

	private Map<String, String> userNameMapMark = new HashMap<String, String>();

	/**
	 * 通过用户名获取该用户对应的通道
	 * 
	 * @param username
	 * @return
	 */
	public Channel getChannelByUsername(String username) {
		return onLineUserConnect.get(username);
	}

	/**
	 * 通过通道标志获取通道
	 * 
	 * @param mark
	 * @return
	 */
	public Channel getChannelByMark(String mark) {
		return markChannelConnect.get(mark);
	}

	/**
	 * 获取在线的所有用户
	 * 
	 * @return
	 */
	public Set<String> getOnLineUsers() {
		return onLineUserConnect.keySet();
	}

	/**
	 * 通过用户名下线
	 * 
	 * @param username
	 */
	public void downMachineByUsername(String username) {
		String mark = userNameMapMark.remove(username);
		onLineUserConnect.remove(username).close();
		markChannelConnect.remove(mark).close();
		markMapUsername.remove(mark);
	}

	/**
	 * 通过通道标志下线
	 * 
	 * @param mark
	 */
	public void downMachineByMark(String mark) {
		String username = markMapUsername.remove(mark);
		onLineUserConnect.remove(username).close();
		markChannelConnect.remove(mark).close();
		userNameMapMark.remove(mark);
	}

	/**
	 * 用户上线
	 * 
	 * @param username
	 * @param channel
	 */
	public void userOnLine(String username, Channel channel) {
		markMapUsername.put(channel.id().asLongText(), username);
		onLineUserConnect.put(username, channel);
		markChannelConnect.put(channel.id().asLongText(), channel);
		userNameMapMark.put(username, channel.id().asLongText());
	}
}
