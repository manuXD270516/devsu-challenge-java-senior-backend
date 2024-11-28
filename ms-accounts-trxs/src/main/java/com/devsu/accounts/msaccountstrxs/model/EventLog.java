package com.devsu.accounts.msaccountstrxs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "event_logs")
public class EventLog {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_log_seq")
    @SequenceGenerator(name = "event_log_seq", sequenceName = "event_log_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "details")
    private String details;
}
