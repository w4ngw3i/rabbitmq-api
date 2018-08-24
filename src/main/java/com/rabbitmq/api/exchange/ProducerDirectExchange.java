package com.rabbitmq.api.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Auther wangwei
 * @Date 2018/8/22 下午6:33
 */
public class ProducerDirectExchange {
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
        String exchangeName = "test_direct_exchage";
        String routingKey = "test_direct";
        String msg = "Hello World RabbitMQ Direct exchange Message...";

        /**
         * 发送消息
         */
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes() );


    }
}
