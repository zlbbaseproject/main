package com.zbin.test;

import java.util.concurrent.*;

/**
 * Created by zhenglibin on 2018/9/26.
 */
public class CylicBarrierTest2 {
    public static void main(String[] args) {

        testConcurrent(3,4);
    }

    /**
     *
     * @param maxConcurrent 最大支持并发数
     * @param maxThread 最大线程
     */
    public static void testConcurrent(int maxConcurrent,int maxThread){
        CyclicBarrier ba = new CyclicBarrier(maxThread,new MyFinalTask());
        Semaphore sem = new Semaphore((maxConcurrent<maxThread?maxConcurrent:maxThread));
        for(int i = 0 ; i < maxThread; i++){
            new Thread(()->{
                try{
                    sem.acquire();
                    System.out.println(Thread.currentThread().getName()+"开始执行");
                    sem.release();
                    ba.await();
                }catch(Exception e){
                    e.printStackTrace();
                }

            },"线程"+i).start();
        }


    }
}
class MyFinalTask implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("执行完成");
        }  catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}