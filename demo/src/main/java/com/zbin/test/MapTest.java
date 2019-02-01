package com.zbin.test;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapTest {

	public static void main(String[] args) {
		ConcurrentNavigableMap map = new ConcurrentSkipListMap();
		map.put("1", "111");
		map.put("2", "222");
		map.put("3", "333");
		map.headMap("2");

		ConcurrentNavigableMap headMap = map.headMap("2");
		System.out.println(headMap);
		System.out.println(map.tailMap("2"));
	}

}
