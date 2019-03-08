package com.zbin.test;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class loaderTest {
	public static void main(String args[]) {
//		ClassLoader a = loaderTest.class.getClassLoader();
//		System.out.println(a);
//		System.out.println(Thread.currentThread().getContextClassLoader());
//		URLClassLoader urllo;
//		Arrays.asList(getExtDirs()).forEach(file->{
//			System.out.println(file.getAbsolutePath());
//		});
		
//		List list = new ArrayList();
//		listNew newl = new listNew(list);
//		listNew newl2 = new listNew(list);
//		new Thread(newl).start();
//		new Thread(newl2).start();
//		while(Thread.activeCount()<=1) {
//			System.out.println(list.size());
//		}
		
		person p = new person("zheng",26);
		person p2 = new person("zheng",27);
		person2 p3 = new person2("zheng",28);
		System.out.println(p == p2);
		System.out.println(p.equals(p2));
		System.out.print(p.hashCode() + "  "+p2.hashCode()+" "+p3.hashCode());
		HashMap map = new HashMap();
		map.put(p, 1);
		map.put(p2, 2);
		map.put(p3, 3);
		System.out.println(map);
//		System.out.println(p.equals(p3));
//		System.out.println("abc".equals("abc"));
//		String test = "abc";
//		System.out.println("abc"=="abc");
//		System.out.println(test=="abc");
	}
	



	//ExtClassLoader���л�ȡ·���Ĵ���
	private static File[] getExtDirs() {
	     //����<JAVA_HOME>/lib/extĿ¼�е����
	     String s = System.getProperty("java.ext.dirs");
	     File[] dirs;
	     
	     if (s != null) {
	         StringTokenizer st =
	             new StringTokenizer(s, File.pathSeparator);
	         System.out.println(s+"     "+File.pathSeparator);
	         int count = st.countTokens();
	         dirs = new File[count];
	         for (int i = 0; i < count; i++) {
	             dirs[i] = new File(st.nextToken());
	         }
	     } else {
	         dirs = new File[0];
	     }
	     return dirs;
	 }
}
class listNew implements Runnable{
	public List list;
	public listNew(List list) {
		super();
		this.list = list;
	}
	@Override
	public void run() {
		for(int i = 0 ;i<1000;i++) {
			list.add(i);
		}
		
	}
	
}

class person {
	private String name;
	private int age;
	public person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	@Override
	public String toString() {
		return "person [name=" + name + ", age=" + age + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		person other = (person) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}

class person2 {
	private String name;
	private int age;
	public person2(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	@Override
	public String toString() {
		return "person2 [name=" + name + ", age=" + age + "]";
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		person2 other = (person2) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}