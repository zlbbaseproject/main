package com.zbin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("1234test2");
		System.out.println("1234test1");
	}

	@Test
	public void contextLoads1() {
		System.out.println("1234test1");
	}

	@Test
	public void contextLoads2() {
		System.out.println("1234test2");
	}
}
