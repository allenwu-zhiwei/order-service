package com.nusiss.orderservice.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OrderTimeoutProducer {

    private static final String EXCHANGE_NAME = "order_exchange";
    private static final String QUEUE_NAME = "order_queue";
    private static final String ROUTING_KEY = "order.timeout";

    public static void sendOrderTimeoutMessage(String orderId) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            String message = "Order " + orderId + " has timed out.";

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent message: '" + message + "'");
        }
    }

    public static void main(String[] args) throws Exception {
        sendOrderTimeoutMessage("12345");
    }
}
