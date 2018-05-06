package com.wuqinghua.devops.demo07.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel sc;

	public ReadCompletionHandler(AsynchronousSocketChannel sc) {
		this.sc = sc;
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();

		System.out.println("获取到数据的大小：" + result);
		String str = new String(attachment.array()).trim();
		System.out.println("获取到结果为：" + str);
		writer("获取到信息");
	}

	private void writer(String string) {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(string.getBytes());
		buf.flip();
		sc.write(buf);
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		exc.printStackTrace();
	}

}
