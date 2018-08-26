package com.rabbitmq.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Auther wangwei
 * @Date 2018/8/24 下午3:52
 */
public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("---consumer message---");

        System.out.println("body:"+ new String(body));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer)properties.getHeaders().get("num") == 0)
            //b1:设置为true表示为重新进入队列
            channel.basicNack(envelope.getDeliveryTag(), false, true);

        /**
         * 每次处理一条消息给生产端一个ack,告诉生产端可以发送下一条ack
         * b:设置为false表示非批量处理
         */
        else
         channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
