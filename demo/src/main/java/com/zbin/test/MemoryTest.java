package com.zbin.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MemoryTest {
	public static int i = 2;
	public static void main(String[] args) {
		jdk8Test();
	}
	
	public static void jdk8Test() {
		class person{
			String name;
			int age;
			LocalDateTime ldt;
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
			public LocalDateTime getLdt() {
				return ldt;
			}
			public void setLdt(LocalDateTime ldt) {
				this.ldt = ldt;
			}
			public person(String name, int age, LocalDateTime ldt) {
				super();
				this.name = name;
				this.age = age;
				this.ldt = ldt;
			}
			@Override
			public String toString() {
				return "person [name=" + name + ", age=" + age + ", ldt=" + ldt + "]";
			}
			
		}
		person p1 = new person("1",20,LocalDateTime.of(2012, 12, 3, 0, 0));
		person p2 = new person("1",20,LocalDateTime.of(2013, 12, 3, 0, 0));
		List<person> list = new ArrayList<person>();
		list.add(p1);
		list.add(p2);
		//list.sort((o1,o2)->o1!=null&& o2 !=null ?o1.getLdt().compareTo(o2.getLdt()):0);
		Collections.sort(list, Comparator.comparing(person::getLdt));
		System.out.println(list);
	}

}
