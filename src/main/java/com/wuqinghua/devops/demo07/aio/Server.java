package com.wuqinghua.devops.demo07.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	protected ExecutorService executor;
	protected AsynchronousChannelGroup group;
	protected AsynchronousServerSocketChannel ssc;

	public Server(int port) {

		try {
			executor = Executors.newCachedThreadPool();
			group = AsynchronousChannelGroup.withCachedThreadPool(executor, 1);
			ssc = AsynchronousServerSocketChannel.open(group);

			// 绑定端口
			ssc.bind(new InetSocketAddress(port));

			System.out.println("server start , port : " + port);

			// 进行阻塞
			ssc.accept(this, new ServerCompletionHandler());

			// 一直阻塞 不让服务器停止
			Thread.sleep(Integer.MAX_VALUE);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server(9999);
	}

}
