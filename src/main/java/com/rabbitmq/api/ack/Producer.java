package com.rabbitmq.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_ack_exchange";
		String routingKey = "ack.save";




		/**
		 * 第3个参数如果设为true则消息转发不到监听器会做后续处理，否则不做任何处理
		 */
		for (int i = 0; i < 5; i++) {
			Map<String, Object> headers = new HashMap<>();
			headers.put("num", i);
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.headers(headers)
					.build();
			String msg = "Hello RabbitMQ ACK Message" + i;

			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}

		
		
		
	}
}
