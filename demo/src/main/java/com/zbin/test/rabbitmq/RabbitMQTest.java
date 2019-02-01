package com.zbin.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by lenovo on 2018/8/4.
 */
public class RabbitMQTest {
    public static final String QUEUE_NAME = "MYFIRSTQUENE";
    public static void main(String args[])throws Exception{

        sendMSG("hello,zhenglibin");
        Thread.sleep(10000);
        recieveMSG();

    }
    public static void sendMSG(String msg) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.106.214.38");
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = msg;
        //发送消息到队列中
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("Producer Send +'" + message + "'");

        channel.close();
        connection.close();
    }

    public static void recieveMSG() throws Exception{
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("47.106.214.38");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Customer Waiting Received messages");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
                channel.basicReject(envelope.getDeliveryTag(),false);
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
