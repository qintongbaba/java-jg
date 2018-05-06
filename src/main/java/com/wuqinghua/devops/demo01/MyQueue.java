package com.wuqinghua.devops.demo01;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟一个阻塞式的queue
 * 
 * @author wuqinghua
 *
 */

public class MyQueue {

	// 1.需要一个存放元素的集合
	private final LinkedList<Object> list = new LinkedList<>();

	// 2.计数器
	private final AtomicInteger count = new AtomicInteger(0);

	// 3.指定上限和下限
	private final int minSize = 0;
	private final int maxSize;

	// 4.构造方法
	public MyQueue(int maxSize) {
		this.maxSize = maxSize;
	}

	// 5.初始化一个对象用于加锁
	private final Object lock = new Object();

	// 向队列中添加元素
	public void put(Object obj) {
		synchronized (lock) {
			while (this.maxSize == count.get()) { // 容器已满
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			list.add(obj);
			System.out.println("新加入元素成功！obj: " + obj);
			count.incrementAndGet(); // 计数器新增

			// 唤醒tack线程
			lock.notify();
		}
	}

	// 从队列中获取元素
	public Object tack() {
		Object ret = null;
		synchronized (lock) {
			while (this.count.get() == minSize) { // 如果为空
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			ret = list.removeFirst();
			System.out.println("获取元素成功! ret: " + ret);
			count.decrementAndGet();

			// 唤醒put
			lock.notify();
		}
		return ret;
	}

	// 进行测试
	public static void main(String[] args) {
		MyQueue myQueue = new MyQueue(5);
		myQueue.put("a");
		myQueue.put("b");
		myQueue.put("c");
		myQueue.put("d");
		myQueue.put("e");

		new Thread(() -> {
			myQueue.put("f");
			myQueue.put("g");
		}).start();

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			myQueue.tack();
			myQueue.tack();
		}).start();

	}

}
