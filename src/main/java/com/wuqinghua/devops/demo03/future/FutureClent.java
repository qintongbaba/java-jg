package com.wuqinghua.devops.demo03.future;

public class FutureClent {

	public Data request(String queryStr) {

		// 1.返回代理对象先给client端
		FutureData futureData = new FutureData();

		// 启动一个新的线程
		new Thread(() -> {
			// 真实的数据
			RealData realData = new RealData(queryStr);
			futureData.setRealData(realData);
		}).start();

		return futureData;
	}

}
