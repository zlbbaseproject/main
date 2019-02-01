package com.zbin.test;

import java.util.Random;

public class DeadLockTest {

	public static void main(String[] args) {
		BankIntface nong = new nongye("农业银行",1000),gong = new gongshang("工商银行",1000);
		Random r = new Random();
		for(int i = 0 ;i < 10;i++) {
			new Thread(new userservice(nong,gong,r.nextInt(30)+1 )).start();

		}

		for(int i = 0 ;i < 10;i++) {

			new Thread(new userservice(gong,nong,r.nextInt(30)+1)).start();
		}
	}

}
//转账服务
class userservice implements Runnable{
	private volatile static int cou = 0;
	BankIntface o1,o2;
	int num;
	public userservice(BankIntface o1, BankIntface o2,int num) {
		super();
		this.o1 = o1;
		this.o2 = o2;
		this.num = num;
		cou++;
	}

	public void run() {
		synchronized(o1) {
			System.out.println(o2.name+"向"+o1.name+"转账"+num+"元");
			o1.cun(num);

			synchronized(o2) {
				o2.qu(num);
			}
		}
	}
}

abstract class BankIntface{

	public int allmoney = 100;
	public String name;
	public synchronized void cun(int num) {
		this.allmoney += num;
	}


	public synchronized void qu(int num) {
		this.allmoney -=num;
	}
}

class nongye extends BankIntface{
	public nongye(String name,int allmoney) {
		this.name = name;
		this.allmoney = allmoney;
	}
}

class gongshang extends BankIntface{
	public gongshang(String name,int allmoney) {
		this.name = name;
		this.allmoney = allmoney;
	}
}