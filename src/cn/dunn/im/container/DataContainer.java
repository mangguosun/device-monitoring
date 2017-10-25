package cn.dunn.im.container;

import java.util.concurrent.ArrayBlockingQueue;

public class DataContainer {
	private static ArrayBlockingQueue<String> friends = new ArrayBlockingQueue<String>(500);

	public static boolean addFriend(String username) {
		return friends.offer(username);
	}
}
