package com.devsu.accounts.msaccountstrxs.messaging;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ClienteConsumer {

    /*@RabbitListener(queues = "cliente.queue")
    public void procesarMensaje(String mensaje) {
        System.out.println("Mensaje recibido: " + mensaje);
        // Implementar lógica para sincronizar datos relacionados al cliente
    }*/
}
