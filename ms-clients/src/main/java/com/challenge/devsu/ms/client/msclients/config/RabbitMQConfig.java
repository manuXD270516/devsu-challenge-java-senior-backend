package com.challenge.devsu.ms.client.msclients.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RabbitMQConfig {

    public static final String CLIENT_EVENTS_QUEUE = "client-events-queue";

    @Bean
    public Queue clientEventsQueue() {
        return new Queue(CLIENT_EVENTS_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("client-exchange");
    }

    @Bean
    public Binding binding(Queue clientEventsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(clientEventsQueue).to(exchange).with("client-routing-key");
    }

    /*@Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(new CachingConnectionFactory("localhost", 5672));
    }*/
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*@Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }*/

   /* @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }*/
}
