package com.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther wangwei
 * @Date 2018/8/22 下午4:52
 */
public class Producer {
    public static void main(String[] args) throws Exception {
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
         * 发送消息
         */
        for (int i = 0; i < 5; i++) {
            String msg = "Hello rabbitMQ";

            channel.basicPublish("", "test001", null, msg.getBytes() );
        }

        channel.close();
        connection.close();
    }
}
