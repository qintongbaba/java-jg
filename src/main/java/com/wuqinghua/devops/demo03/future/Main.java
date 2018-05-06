package com.wuqinghua.devops.demo03.future;

public class Main {
	
	public static void main(String[] args) {

		FutureClent fc = new FutureClent();
		Data data = fc.request("请求参数");
		System.out.println("发送请求成功！");
		System.out.println("处理其他的事情....");

		String request = data.getRequest();
		System.out.println(request);
	}

}
