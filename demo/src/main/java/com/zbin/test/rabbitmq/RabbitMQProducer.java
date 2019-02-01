package com.zbin.test.rabbitmq;

/**
 * Created by TDW on 2019/1/29.
 */
public class RabbitMQProducer {
    public static void main(String args[])throws Exception{
        RabbitMQTest.sendMSG("hello,zhenglibin");

    }
}
