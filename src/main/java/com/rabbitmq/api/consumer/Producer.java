package com.rabbitmq.api.consumer;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_consumer_exchange";
		String routingKey = "consumer.save";

		
		String msg = "Hello RabbitMQ Consumer Message";
		

		/**
		 * 第3个参数如果设为true则消息转发不到监听器会做后续处理，否则不做任何处理
		 */
		for (int i = 0; i < 5; i++) {

			channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
		}

		
		
		
	}
}
