package com.wuqinghua.devops.demo04;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量限流的一个demo
 * 
 * @author wuqinghua
 *
 */
public class UseSemaphore {

	public static void main(String[] args) {
		// 线程池
		ExecutorService threadPool = Executors.newCachedThreadPool();

		// 创建信号量
		Semaphore semaphore = new Semaphore(5);

		for (int i = 0; i < 20; i++) {
			int no = i;
			threadPool.submit(() -> {
				try {
					// 获取许可
					semaphore.acquire();
					System.out.println("Process " + no);
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
					System.out.println("End " + no);
					System.out.println("--------------------------------------");
					semaphore.release(); // 释放
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
