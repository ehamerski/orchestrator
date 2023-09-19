package com.example.spring.boot.camel.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class OrchestratorApplication {

	public static void main(String[] args) {
		System.setProperty("APPLICATION_ID", UUID.randomUUID().toString());
		SpringApplication.run(OrchestratorApplication.class, args);
	}
}
