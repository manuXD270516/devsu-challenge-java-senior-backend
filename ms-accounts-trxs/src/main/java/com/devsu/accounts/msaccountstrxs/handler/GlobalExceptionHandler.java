package com.devsu.accounts.msaccountstrxs.handler;

import com.devsu.accounts.msaccountstrxs.exceptions.ResourceNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_ERROR.getCode())
                .message("Ocurrió un error inesperado. Por favor, contacte al soporte.")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @Builder
    private static class ErrorResponse {
        private String errorCode;
        private String message;
        private LocalDateTime timestamp;
    }



    @Getter
    @Builder
    public static  class ErrorCode {
        private final String code;
        private final String message;

        public static final ErrorCode RESOURCE_NOT_FOUND = ErrorCode.builder()
                .code("RESOURCE_NOT_FOUND")
                .message("El recurso solicitado no fue encontrado")
                .build();

        public static final ErrorCode VALIDATION_FAILED = ErrorCode.builder()
                .code("VALIDATION_FAILED")
                .message("Los datos proporcionados no son válidos")
                .build();

        public static final ErrorCode INTERNAL_ERROR = ErrorCode.builder()
                .code("INTERNAL_ERROR")
                .message("Ocurrió un error inesperado")
                .build();
    }
}
