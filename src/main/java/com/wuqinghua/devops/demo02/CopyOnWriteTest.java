package com.wuqinghua.devops.demo02;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Coyp-On-Write --> COW
 * <p>
 * 写时复制的容器，对一个容器进行操作的时候，不是直接操作当前的容器， 而是将当前容器进行copy变为一个新的容器，当操作完成后将当前容器指向新容器。
 * 也就是读写分离的思想。
 * <p>
 * 
 * CoypOnWriteArrayList
 * <p>
 * CopyOnWriteArraySet
 * 
 * @author wuqinghua
 *
 */
public class CopyOnWriteTest {
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
	}
}
