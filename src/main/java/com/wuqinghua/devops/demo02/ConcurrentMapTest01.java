package com.wuqinghua.devops.demo02;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * ConcurrentHashMap --> HashTable
 * <p>
 * ConcurrentSkipListMap --> TreeMap
 * 
 * 
 * 
 * @author wuqinghua
 *
 */
public class ConcurrentMapTest01 {
	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		ConcurrentSkipListMap<String, String> map1 = new ConcurrentSkipListMap<>();
	}
}
