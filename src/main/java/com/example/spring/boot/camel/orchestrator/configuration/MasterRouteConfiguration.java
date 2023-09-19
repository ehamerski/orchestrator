package com.example.spring.boot.camel.orchestrator.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterRouteConfiguration extends RouteBuilder {

	@Override
	public void configure() {
		from("master:{{node.namespace}}:timer://masterTimer?period=3000")
			.routeId("master-route-01")
			.log("master-route-01 (timer) {{node.id}} ...");

		from("master:{{node.namespace}}:timer://masterTimer?period=5000")
			.routeId("master-route-02")
			.log("master-route-02 (timer) {{node.id}} ...");

		from("master:{{node.namespace}}:timer://masterTimer?period=7000")
			.routeId("master-route-03")
			.log("master-route-03 (timer) {{node.id}} ...");
	}

}