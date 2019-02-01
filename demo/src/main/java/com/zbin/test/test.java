package com.zbin.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.*;

public class test {

	public static void main(String[] args) {
		testss();
		HashMap hs;
		System.out.println((7 & 2));
		System.out.println((7 & 9));
	}

	public static void testss(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("执行");
				return "123";
			}
		};
		Future<String> f = executorService.submit(callable);
		executorService.submit(callable);
		try {
			System.out.println(f.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}
}
