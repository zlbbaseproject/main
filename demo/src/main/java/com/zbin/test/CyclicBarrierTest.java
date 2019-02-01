package com.zbin.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	public static void main(String[] args) {

		CyclicBarrier ba = new CyclicBarrier(2,new stak2());
		new Thread(new stak(ba)) .start();
		new Thread(new stak(ba)) .start();
		new Thread(new stak(ba)) .start();
		new Thread(new stak(ba)) .start();
	}

}
class stak implements Runnable{
	private CyclicBarrier ba;

	public stak(CyclicBarrier ba) {
		super();
		this.ba = ba;
	}

	@Override
	public void run() {
		try {

			Thread.sleep(1000);
			System.out.println("renwu线程");
			ba.await();
			System.out.println("执行栅栏线程后续行动");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class stak2 implements Runnable{


	@Override
	public void run() {
		System.out.println("回调线程");

	}

}