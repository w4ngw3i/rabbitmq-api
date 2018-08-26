package com.rabbitmq.api.consumer;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;

/**
 * @Auther wangwei
 * @Date 2018/8/24 下午3:52
 */
public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("---consumer message---");
        System.out.println("consumerTag:--"+consumerTag);
        System.out.println("envelope:"+envelope);
        System.out.println("properties"+properties);
        System.out.println("body:"+new String(body));
    }
}
