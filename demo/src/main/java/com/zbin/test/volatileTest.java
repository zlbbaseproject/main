package com.zbin.test;

public class volatileTest {

	public static void main(String[] args) throws InterruptedException {
		resource re = new resource();
		new Thread(new testRun(re)).start();
		new Thread(new testRun(re)).start();
		Thread.sleep(1000);
		re.setBool(true);
	}

}
class testRun implements Runnable{
	resource re;

	public testRun(resource re) {
		this.re = re;
	}

	public void run() {
		int time = 10;
		while(time>0){
			System.out.println(re.getBool());
			try {
				Thread.sleep(1000);
				time--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class resource{
	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	private volatile  Boolean bool = false;

}
