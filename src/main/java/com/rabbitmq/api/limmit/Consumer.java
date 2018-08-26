package com.rabbitmq.api.limmit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("39.107.234.188");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		String exchangeName = "test_qos_exchange";
		String routingKey = "qos.#";
		String queueName = "test_qos_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		/**
		 * i:不对消息大小进行限制
		 * i1:当消息为处理完每次限制一条信息过来
		 * b:应用于当前consumer
		 */
		channel.basicQos(0, 1, false);

		/**
		 * 使用限流qos必须关闭autoack自动签收，也就是第二个参数设置为false
		 */
		channel.basicConsume(queueName, false, new MyConsumer(channel));
		
		
	}
}
