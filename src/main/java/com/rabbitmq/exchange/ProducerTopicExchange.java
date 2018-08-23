package com.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Auther wangwei
 * @Date 2018/8/22 下午6:33
 */
public class ProducerTopicExchange {
    public static void main(String[] args) throws Exception{
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

        //声明
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        String msg = "Hello World RabbitMQ Topic Exchange Message...";

        /**
         * 发送消息
         */
        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes() );
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes() );
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes() );

        channel.close();
        connection.close();


    }
}
