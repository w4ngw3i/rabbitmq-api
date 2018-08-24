package com.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Auther wangwei
 * @Date 2018/8/24 上午11:31
 */
public class Producer {
    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.234.188");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //指定消息投递模式为确认模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        //发送一条消息
        String msg = "Hello RabbitMQ Send confirm message";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("----ack----");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {

                System.out.println("----No ack-----");
            }
        });

    }
}
