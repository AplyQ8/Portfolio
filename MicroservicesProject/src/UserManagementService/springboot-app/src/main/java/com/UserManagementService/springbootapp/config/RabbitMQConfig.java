package com.UserManagementService.springbootapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //spring bean for RabbitMQ
    @Value("${rabbitmq.queue.name}")
    private String _queue;
    @Value("${rabbitmq.exchange.name}")
    private String _exchange;

    @Bean
    public Queue queue(){
        return new Queue(_queue);
    }
    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(_exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange());
    }
}
