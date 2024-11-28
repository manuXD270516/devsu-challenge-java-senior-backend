package com.challenge.devsu.ms.client.msclients.handler;

import com.challenge.devsu.ms.client.msclients.exceptions.ResourceNotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorCode> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorCode errorCode = ErrorCode.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path("/clientes/{id}") // Aquí puedes automatizar el path si es necesario
                .build();
        return new ResponseEntity<>(errorCode, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error occurred: " + ex.getMessage());
    }

    @Data
    @Builder
    private static class ErrorCode {

        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

    }
}
