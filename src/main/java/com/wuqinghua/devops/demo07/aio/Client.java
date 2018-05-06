package com.wuqinghua.devops.demo07.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class Client {

	private AsynchronousSocketChannel sc;

	public Client() throws IOException {
		sc = AsynchronousSocketChannel.open();
	}

	public void connect(String host, int port) throws InterruptedException, ExecutionException {
		sc.connect(new InetSocketAddress(host, port)).get();
	}

	public void write(String ret) throws InterruptedException, ExecutionException {
		sc.write(ByteBuffer.wrap(ret.getBytes()));
	}

	public String read() throws InterruptedException, ExecutionException, UnsupportedEncodingException {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		sc.read(buf);
		buf.flip();
		byte[] bytes = new byte[buf.remaining()];
		return new String(bytes, "UTF-8");
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		Client c1 = new Client();
		c1.connect("127.0.0.1", 9999);
		c1.write("c1 aaaa");
		
		System.out.println(c1.read());
	}

}
