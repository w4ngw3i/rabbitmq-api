package com.rabbitmq.api.returnlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_return_exchange";
		String routingKey = "return.#";
		String queueName = "test_return_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while(true){
			
			Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.err.println("消费者: " + msg);
		}
		
		
		
		
		
	}
}
