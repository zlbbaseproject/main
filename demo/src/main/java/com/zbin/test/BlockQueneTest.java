package com.zbin.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * test
 */
public class BlockQueneTest {
	public static void main(String args[]) throws InterruptedException {
		BlockingQueue queue = new ArrayBlockingQueue(1024);
        ConcurrentHashMap map;
        producer pro = new producer(queue);
		consumer con = new consumer(queue);
 
        new Thread(pro).start();
        new Thread(con).start();
 
        Thread.sleep(4000);

	}
}


class producer implements Runnable{
	protected BlockingQueue queue = null;
	 
    public producer(BlockingQueue queue) {
        this.queue = queue;
    }

	@Override
	public void run() {
		try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}

}
class consumer implements Runnable{
	protected BlockingQueue queue = null;
	 
    public consumer(BlockingQueue queue) {
        this.queue = queue;
    }

	@Override
	public void run() {
		try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


	}

}
