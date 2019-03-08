package com.zbin.test.zkTest;

/**
 * Created by TDW on 2019/3/8.
 */
public class ZkConfigTest {
    public static void main(String[] args) {
        ZkConfigMag mag = new ZkConfigMag();
        Config config = mag.downLoadConfigFromDB();
        System.out.println("....加载数据库配置...."+config.toString());
        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");

        //歇会，这样看比较清晰
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mag.upLoadConfigToDB("cwhcc", "passwordcc");
        System.out.println("....修改配置文件...."+config.toString());
        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");


    }
}
