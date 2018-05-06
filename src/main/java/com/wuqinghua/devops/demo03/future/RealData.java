package com.wuqinghua.devops.demo03.future;

import java.util.concurrent.TimeUnit;

public class RealData implements Data {

	private String result;

	public RealData(String queryStr) {
		System.out.println("根据" + queryStr + "进行查询，这是一个耗时的	操作.");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("操作完毕");
		result = "查询结果";
	}

	@Override
	public String getRequest() {
		return result;
	}
}
