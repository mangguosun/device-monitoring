package cn.dunn.im.constant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadContainer {
	public static final ExecutorService EXECUTOR_THREAD_POOL = Executors.newFixedThreadPool(10);
}
