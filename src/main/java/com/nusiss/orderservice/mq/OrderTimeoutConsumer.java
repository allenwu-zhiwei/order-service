package com.nusiss.orderservice.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class OrderTimeoutConsumer {

    private static final String EXCHANGE_NAME = "order_exchange";
    private static final String QUEUE_NAME = "order_queue";
    private static final String ROUTING_KEY = "order.timeout";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received message: '" + message + "'");

                // 处理订单超时逻辑
                handleOrderTimeout(message);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        }
    }

    private static void handleOrderTimeout(String message) {
        // 在这里处理订单超时逻辑，例如取消订单
        String orderId = message.split(" ")[1];
        System.out.println("Handling order timeout for order ID: " + orderId);
        // 可以调用业务逻辑方法取消订单
    }
}
