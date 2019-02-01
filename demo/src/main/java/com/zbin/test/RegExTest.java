package com.zbin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTest {
	public static void main(String args[]) {
		testOne();
	}

	public static void testOne() {
		String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
		String str =  "linfen@xsoft.net";
		Pattern pattern = Pattern.compile(regex);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		boolean rs = matcher.find();
		System.out.println(rs);
	}
}
