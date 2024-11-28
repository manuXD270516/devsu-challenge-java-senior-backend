package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.config.RabbitMQConfig;
import com.devsu.accounts.msaccountstrxs.model.EventLog;
import com.devsu.accounts.msaccountstrxs.repository.EventLogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientEventConsumer {

    @Autowired
    private EventLogRepository eventLogRepository;

    @RabbitListener(queues = RabbitMQConfig.CLIENT_EVENTS_QUEUE)
    public void handleClientEvent(Map<String, Object> event) {
        EventLog log = new EventLog();
        try {
            // Extraer datos del evento
            String tipoEvento = event.getOrDefault("tipoEvento", "DESCONOCIDO").toString();
            Long clientId = event.get("clientId") != null ? Long.valueOf(event.get("clientId").toString()) : null;
            String nombre = event.getOrDefault("nombre", "N/A").toString();
            boolean estado = Boolean.parseBoolean(event.getOrDefault("estado", "false").toString());

            // Lógica de negocio con logs
            switch (tipoEvento) {
                case "CREAR_CLIENTE":
                    System.out.printf("Evento recibido: CREAR_CLIENTE, Cliente ID=%d, Nombre=%s, Estado=%b%n",
                            clientId, nombre, estado);
                    break;

                case "ACTUALIZAR_CLIENTE":
                    System.out.printf("Evento recibido: ACTUALIZAR_CLIENTE, Cliente ID=%d, Nombre=%s, Estado=%b%n",
                            clientId, nombre, estado);
                    break;

                case "ELIMINAR_CLIENTE":
                    System.out.printf("Evento recibido: ELIMINAR_CLIENTE, Cliente ID=%d, Nombre=%s%n",
                            clientId, nombre);
                    break;

                default:
                    System.err.println("Evento desconocido: " + tipoEvento);
                    //throw new IllegalArgumentException("Evento desconocido: " + tipoEvento);
            }

            // Registrar log de éxito
            log.setEventType(tipoEvento);
            log.setClientId(clientId);
            log.setStatus("SUCCESS");
            log.setDetails("Evento procesado exitosamente.");

        } catch (Exception e) {
            // Registrar log de error
            log.setStatus("FAILED");
            log.setDetails("Error al procesar el evento: " + e.getMessage());
        }

        // Guardar log en la base de datos
        eventLogRepository.save(log);
    }
}
