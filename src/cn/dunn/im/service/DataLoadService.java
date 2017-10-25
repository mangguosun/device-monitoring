package cn.dunn.im.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.Node;

public enum DataLoadService {
	INSTANCE;
	private ExecutorService threadPool = Executors.newCachedThreadPool();

	public void friendsDateLoad(Node friends) {
		threadPool.execute(new FriendsDateLoadThread(friends));
	}

	public void test(final Node node) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (node == null) {
					System.out.println("正在加载");
					Thread.yield();
				}
				System.out.println("加载完成");
				System.out.println(node);
			}
		});
	}

	private class FriendsDateLoadThread implements Runnable {
		private Node friends;

		public FriendsDateLoadThread(Node friends) {
			this.friends = friends;
		}

		@Override
		public void run() {
			while (friends == null) {
				System.out.println("-------------");
				Thread.yield();
			}
			System.out.println(friends);
		}

	}
}
