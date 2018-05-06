package com.wuqinghua.devops.demo05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * 
 * 读写锁ReentrantReadWriteLock 
 * 
 * @author wuqinghua
 *
 */
public class UseReentrantLock {
	private ReentrantLock lock = new ReentrantLock();

	public void method1() {
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method1");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "退出method1");
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void method2() {
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method2");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "退出method2");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		UseReentrantLock useReentrantLock = new UseReentrantLock();

		new Thread(() -> {
			useReentrantLock.method1();
			useReentrantLock.method2();
		}).start();
	}
}
