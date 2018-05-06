package com.wuqinghua.devops.demo06.test01;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class LongEventProducer {

	private RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(ByteBuffer buf) {
		long sequence = ringBuffer.next(); // 获取下个ringBuffer中的索引
		try {
			LongEvent longEvent = ringBuffer.get(sequence);
			longEvent.setValue(buf.getLong(0));
		} finally {
			ringBuffer.publish(sequence); // 发布数据
		}

	}

}
