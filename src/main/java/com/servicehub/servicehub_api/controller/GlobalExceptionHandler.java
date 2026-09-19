package com.servicehub.servicehub_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarErro(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("erro", ex.getMessage()));
    }
}