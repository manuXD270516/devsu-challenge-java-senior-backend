package com.challenge.devsu.ms.client.msclients.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClientEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    public ClientEventPublisher(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void publishClientEvent(Long clientId, String nombre, boolean estado) {
        Map<String, Object> event = new HashMap<>();
        event.put("tipoEvento", "CREAR_CLIENTE"); // O el tipo correspondiente
        event.put("clientId", clientId);
        event.put("nombre", nombre);
        event.put("estado", estado);

        rabbitTemplate.convertAndSend(exchange.getName(), "client-routing-key", event);
        System.out.println("Event published: " + event);
    }
}
