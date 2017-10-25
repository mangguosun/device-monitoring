package cn.dunn.im.container;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkThreadContainer {
	public static ArrayBlockingQueue<Runnable> desktopTask = new ArrayBlockingQueue<Runnable>(500);
	public static ExecutorService desktopThread = Executors.newCachedThreadPool();
}
