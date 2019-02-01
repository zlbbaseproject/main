package com.zbin.test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	public static void main(String[] args) {
		rwOper();

	}

	//同对象读读操作
	public static void rrOper() {
		ReadWriteLock lock = new ReentrantReadWriteLock(true);
		rwTest rw1 = new rwTest(lock);
		for(int i = 0; i < 10;i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					rw1.read();

				}

			},"读线程"+i).start();
		}
	}

	//同对象读写操作
	public static void rwOper() {
		ReadWriteLock lock = new ReentrantReadWriteLock(true);
		rwTest rw1 = new rwTest(lock);

		for(int i = 0; i < 10;i++) {
			if(i%2==0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						rw1.read();
					}

				},"读线程"+i).start();
			}else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						rw1.write();
					}

				},"xie线程"+i).start();
			}
		}
	}

	//不同对象读读操作
	public static void difrrOper() {
		ReadWriteLock lock = new ReentrantReadWriteLock(true);
		rwTest rw1 = new rwTest(lock);
		rwTest rw2 = new rwTest(lock);
		for(int i = 0; i < 10;i++) {
			if(i%2==0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						rw1.read();
					}

				},"读线程"+i).start();
			}else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						rw2.read();
					}

				},"2读线程"+i).start();
			}
		}
	}

}

class rwTest {
	private ReadWriteLock lock ;
	public rwTest(ReadWriteLock lock) {
		super();
		this.lock = lock;
	}
	public void read() {
		lock.readLock().lock();
		try {
			for(int i = 0 ; i < 100;i++) {
				System.out.println(Thread.currentThread().getName()+" 执行读操作"+i);
			}
		}finally {
			lock.readLock().unlock();
		}

	}
	public void write() {
		lock.writeLock().lock();
		try {
			for(int i = 0 ; i < 100;i++) {
				System.out.println(Thread.currentThread().getName()+" 写操作"+i);
			}
		}finally {
			lock.writeLock().unlock();
		}

	}
}
