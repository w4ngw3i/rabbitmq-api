package com.rabbitmq.api.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @Auther wangwei
 * @Date 2018/8/22 下午6:38
 */
public class ConsumerFanoutExchange {
    public static void main(String[] args) throws Exception{
        /**
         * 创建一个ConnectionFactory连接工厂并配置相关属性
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.234.188");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        //自动重连
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        /**
         * 通过连接工厂创建连接
         */
        Connection connection = factory.newConnection();

        /**
         * 通过connection创建一个channel
         */
        Channel channel = connection.createChannel();

        /**
         * 声明
         */
        String exchangeName = "test_fanout_exchage";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = "";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 创建消费者
         */
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //参数：队列名称 是否自动ACK Consumer
        channel.basicConsume(queueName, true, queueingConsumer);

        /**
         * 获取消息
         */
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息:"+msg);
        }



    }
}
