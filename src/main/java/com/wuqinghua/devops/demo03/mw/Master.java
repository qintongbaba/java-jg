package com.wuqinghua.devops.demo03.mw;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Master {

	// 1.存放所有task的集合
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

	// 2.存放所有的worker的集合
	private HashMap<String, Worker> workers = new HashMap<>();

	// 3.存放结果集合集合
	private ConcurrentHashMap<String, Object> results = new ConcurrentHashMap<>();

	// 4.线程计数器
	private CountDownLatch countDownLatch;

	private Worker worker = new Worker();

	private int count;

	// 5.初始化woker数
	public Master(int count, TaskHandler taskHandler) {
		this.count = count;
		// 计数器初始化
		countDownLatch = new CountDownLatch(count);

		// woker 中需要任务集合和结果集合，以及计数器
		worker.setTaskQueue(this.taskQueue);
		worker.setResults(this.results);
		worker.setCountDownLatch(this.countDownLatch);
		worker.setTaskHandler(taskHandler);

		// 初始化worker到集合中
		for (int i = 0; i < count; i++) {
			workers.put("节点:" + Integer.toString(i), worker);
		}
	}

	// 6.提交任务
	public void submit(Task task) {
		this.taskQueue.add(task);
	}

	// 7.执行任务
	public void execute() {
		for (Entry<String, Worker> me : this.workers.entrySet()) {
			ThreadPool.getPool(count).execute(me.getValue());
		}
	}

	// 8.获取结果集
	public Map<String, Object> getResults() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.results;
	}
}
