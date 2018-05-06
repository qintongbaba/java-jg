package com.wuqinghua.devops.demo03.mw.test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.wuqinghua.devops.demo03.mw.Master;
import com.wuqinghua.devops.demo03.mw.Task;
import com.wuqinghua.devops.demo03.mw.TaskHandler;

public class TestMain {

	public static void main(String[] args) {
		Master master = new Master(Runtime.getRuntime().availableProcessors() * 2, new TaskHandler() {

			@Override
			public Object handler(Task task) {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return task.getData();
			}
		});

		for (int i = 0; i < 100; i++) {
			Task task = new Task();
			task.setId(i);
			task.setName("任务:" + i);
			task.setData(i);
			master.submit(task);
		}

		long begin = System.currentTimeMillis();
		master.execute();

		Map<String, Object> results = master.getResults();
		int sum = 0;
		for (Entry<String, Object> me : results.entrySet()) {
			sum += (int) me.getValue();
		}

		long end = System.currentTimeMillis();
		System.out.println("执行时间为:" + (end - begin) + " 结果为：" + sum);
	}

}
