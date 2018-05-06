package com.wuqinghua.devops.demo03.mw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	private ThreadPoolExecutor executor = null;

	private static int count;

	private static class ThreadPoolHolder {
		private static ThreadPool INSTANCE = new ThreadPool(count, count * 2, new ArrayBlockingQueue<>(100));
	}

	public static ThreadPool getPool(int _count) {
		count = _count;
		return ThreadPoolHolder.INSTANCE;
	}

	private ThreadPool(int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue) {
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 600L, TimeUnit.SECONDS, workQueue);
	}

	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}

}
