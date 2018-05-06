package com.wuqinghua.devops.demo08.test01;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperBase {
	/** zookeeper地址 **/
	static final String CONNECT_ADDR = "172.16.88.144:2181,172.16.88.145:2181,172.16.88.146:2181";

	/** session超时时间 **/
	static final int SESSION_OUTTIME = 5000;// ms

	/** 用于等待zookeeper链接成功的信号量 **/
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// 获取事件
				KeeperState keeperState = event.getState();
				EventType eventType = event.getType();

				// 如果是建立链接
				if (KeeperState.SyncConnected == keeperState) {
					if (EventType.None == eventType) {
						// 如果链接成功
						connectedSemaphore.countDown();
						System.out.println("zk 建立链接");
					}
				}
			}
		});

		// 进行阻塞
		connectedSemaphore.await();
	}
}
