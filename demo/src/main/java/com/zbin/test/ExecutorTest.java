package com.zbin.test;

import java.util.concurrent.*;

public class ExecutorTest {

	public static void main(String[] args) {

		submitCallable();
	}

	public static void testExecutors(){
		ExecutorService executorService = Executors.newFixedThreadPool(20);

	}
	public static void executeTest() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();  
		 
		executorService.execute(new Runnable() {  
		    public void run() {  
		        System.out.println("Asynchronous task");  
		    }  
		});  
		 
		executorService.shutdown();
	}
	public static void submitCallable(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future f = executorService.submit(new Callable<String>(){

			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				return "100";
			}
		});
		try {
			System.out.println(f.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
	public static void submitTest() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();  

			Future f = executorService.submit(new Runnable() {
				public void run() {
					System.out.println("Asynchronous task");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		try {
			System.out.println(f.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
	}

}
