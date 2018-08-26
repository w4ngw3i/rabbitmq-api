package com.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";

		
		String msg = "Hello RabbitMQ dlx Message";
		

		/**
		 * 第3个参数如果设为true则消息转发不到监听器会做后续处理，否则不做任何处理
		 */
		for (int i = 0; i < 1; i++) {
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();

			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}

		
		
		
	}
}
