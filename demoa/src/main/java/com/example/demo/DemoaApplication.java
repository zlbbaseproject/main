package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.zbin.util.*;

@SpringBootApplication
public class DemoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoaApplication.class, args);
	}

	public void run(String... strings) throws Exception {
		System.out.println(Cryptography.tripleDESEncrypt("1","1"));
	}

}

