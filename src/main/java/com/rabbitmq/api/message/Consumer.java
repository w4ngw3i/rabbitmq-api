package com.rabbitmq.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Map;

/**
 * @Auther wangwei
 * @Date 2018/8/22 下午4:52
 */
public class Consumer {
    public static void main(String[] args)throws Exception {
        /**
         * 创建一个ConnectionFactory连接工厂并配置相关属性
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.234.188");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        /**
         * 通过连接工厂创建连接
         */
        Connection connection = factory.newConnection();

        /**
         * 通过connection创建一个channel
         */
        Channel channel = connection.createChannel();

        /**
         * 声明一个队列
         */
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 创建消费者
         */
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        /**
         * 设置channel
         */
        channel.basicConsume(queueName, true, queueingConsumer);

        /**
         * 获取消息
         */
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端:"+msg);

            Map<String, Object> headers = delivery.getProperties().getHeaders();
            System.out.println("headers 中携带的值:" + headers.get("my1"));
        }
    }
}
