package com.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_dlx_exchange";
		String routingKey = "dlx.#";
		String queueName = "test_dlx_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);

		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "dlx.exchange");

		//这个arguments要设置到声明队列上
		channel.queueDeclare(queueName, true, false, false, arguments);
		channel.queueBind(queueName, exchangeName, routingKey);

		//要进行死信队列的声明
		channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
		channel.queueDeclare("dlx.queue", true, false, false, null);
		channel.queueBind("dlx.queue", "dlx.exchange", "#");

		channel.basicConsume(queueName, true, new MyConsumer(channel));
		
		
	}
}
