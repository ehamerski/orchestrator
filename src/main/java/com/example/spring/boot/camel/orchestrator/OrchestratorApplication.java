package com.example.spring.boot.camel.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class OrchestratorApplication {
	public static final String APPLICATION_ID_PROPERTY_NAME = "APPLICATION_ID";
	public static void main(String[] args) {
		System.setProperty(APPLICATION_ID_PROPERTY_NAME, UUID.randomUUID().toString());
		SpringApplication.run(OrchestratorApplication.class, args);
	}
}
