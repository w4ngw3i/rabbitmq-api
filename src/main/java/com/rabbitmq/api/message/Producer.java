package com.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", 111);
        headers.put("my2", 222);

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .headers(headers)
                .build();

        /**
         * 发送消息
         */
        for (int i = 0; i < 5; i++) {
            String msg = "Hello rabbitMQ";

            channel.basicPublish("", "test001", properties, msg.getBytes() );
        }

        channel.close();
        connection.close();
    }
}
