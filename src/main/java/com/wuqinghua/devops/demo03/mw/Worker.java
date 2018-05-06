package com.wuqinghua.devops.demo03.mw;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

	@Override
	public void run() {
		// 执行业务
		Task task = null;
		while ((task = this.taskQueue.poll()) != null) {
			Object ret = taskHandler.handler(task);
			results.put(task.getName(), ret);
		}

		// 计数器进行减一
		countDownLatch.countDown();
	}

	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();
	private ConcurrentHashMap<String, Object> results = new ConcurrentHashMap<>();
	private CountDownLatch countDownLatch;
	private TaskHandler taskHandler;

	public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void setResults(ConcurrentHashMap<String, Object> results) {
		this.results = results;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	public void setTaskHandler(TaskHandler taskHandler) {
		this.taskHandler = taskHandler;
	}

}
