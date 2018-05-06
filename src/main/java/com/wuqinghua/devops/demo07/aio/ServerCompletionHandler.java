package com.wuqinghua.devops.demo07.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

	@Override
	public void completed(AsynchronousSocketChannel sc, Server attachment) {
		// 当有下个客户端接入的时候，直接调用server的accept，这样反复调用，保证多个客户端都可以阻塞
		attachment.ssc.accept(attachment, this);
		read(sc);
	}

	@Override
	public void failed(Throwable exc, Server attachment) {
		exc.printStackTrace();
	}

	private void read(AsynchronousSocketChannel sc) {
		// 读取数据
		ByteBuffer buf = ByteBuffer.allocate(1024);
		sc.read(buf, buf, new ReadCompletionHandler(sc));
	}

}
