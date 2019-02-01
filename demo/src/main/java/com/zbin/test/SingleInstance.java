package com.zbin.test;

/**
 * Created by zhenglibin on 2018/8/13.
 */
public class SingleInstance {
    private volatile static SingleInstance instance = null;
    private SingleInstance() {
        System.out.println("new");
    }

    public static SingleInstance getInstance1(){
        if(instance == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance =  new SingleInstance();
        }
        return instance;
    }


    public synchronized static SingleInstance getInstance2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(instance == null){
            instance =  new SingleInstance();
        }
        return instance;
    }


    public static SingleInstance getInstance(){
        if(instance == null){
            synchronized(SingleInstance.class){
                if(instance == null){
                    instance =  new SingleInstance();
                }
            }
        }
        return instance;
    }



    public void test(){
        System.out.print("1");
    }

    public static void main(String args[]){
        for(int i = 0 ; i<10;i++){
            new Thread(new testins()).start();
        }

    }
}
class testins implements  Runnable{
    @Override
    public void run() {
        SingleInstance.getInstance2().test();
    }
}
