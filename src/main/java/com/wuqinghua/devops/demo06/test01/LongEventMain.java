package com.wuqinghua.devops.demo06.test01;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// 创建工厂
		LongEventFactory factory = new LongEventFactory();

		// 创建ringBuffer，大小需要是的2的n次方
		int ringBufferSize = 1024 * 1024;

		// 创建一个Disruptor

		// 第三个参数：表示生产者的多少 (一个或多个) 第四个参数：表示生成和消费的策略
		Disruptor<LongEvent> disruptor = new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory(),
				ProducerType.SINGLE, new YieldingWaitStrategy());

		// 连接消费事件的方法
		disruptor.handleEventsWith(new LongEventHandler()); // 消费者

		// 启动
		disruptor.start();

		// 发布数据
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		LongEventProducer producer = new LongEventProducer(ringBuffer); // 生产者

		ByteBuffer buf = ByteBuffer.allocate(8);
		for (long l = 0; l < 100; l++) {
			buf.putLong(0, l);
			producer.onData(buf);
			// buf.clear();
		}

		disruptor.shutdown();

	}
}
