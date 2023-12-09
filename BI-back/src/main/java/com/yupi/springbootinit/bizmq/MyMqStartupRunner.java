package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yupi.springbootinit.constant.BiMqConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class MyMqStartupRunner {
    public static void main(String[] args) {
        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("114.115.151.230");
            factory.setPort(5672);
            factory.setUsername("wang"); // RabbitMQ 用户名
            factory.setPassword("123456"); // RabbitMQ 密码
            // 创建连接
            Connection connection = factory.newConnection();
            // 创建通道
            Channel channel = connection.createChannel();
            // 定义交换机的名称为 "code_exchange"
            String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
            // 声明交换机，指定交换机类型为 direct
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 创建队列，随机分配一个队列名称
            String queueName = BiMqConstant.BI_QUEUE_NAME;

            // 创建死信队列
            String queueDeadName = BiMqConstant.BI_DEAD_QUEUE_NAME;

            Map<String, Object> queueArgs = new HashMap<>();

            // 设置死信队列参数
            queueArgs.put("x-max-length", 10); // 设置队列的最大长度为1000
            queueArgs.put("x-message-ttl", 1000); // 设置消息的过期时间为60秒
            queueArgs.put("x-dead-letter-exchange", EXCHANGE_NAME); // 交换机名
            queueArgs.put("x-dead-letter-routing-key", BiMqConstant.BI_DEAD_ROUTING_KEY); // 死信队列名

            // 声明队列，设置队列持久化、非独占、非自动删除，并传入额外的参数
            channel.queueDeclare(queueName, true, false, false, queueArgs);
            Map<String, Object> queueArg = new HashMap<>();
            // 设置死信队列参数
            queueArgs.put("x-max-length", 4); // 设置队列的最大长度为1000
            channel.queueDeclare(queueDeadName, true, false, false, queueArg);

            // 将队列绑定到交换机，指定路由键为 "my_routingKey"
            channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);
            channel.queueBind(queueDeadName, EXCHANGE_NAME, BiMqConstant.BI_DEAD_ROUTING_KEY);
        } catch (Exception e) {
            // 异常处理
        }
    }
}
