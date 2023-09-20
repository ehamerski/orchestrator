package com.example.spring.boot.camel.orchestrator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRouteIdException extends RuntimeException {
    public InvalidRouteIdException(String message) {
        super(message);
    }
}
