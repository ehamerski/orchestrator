package com.example.spring.boot.camel.orchestrator.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.file.cluster.FileLockClusterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleRouteConfiguration extends RouteBuilder {

	@Override
	public void configure() {
		from("timer://simpleTimer?period=2000")
			.routeId("route-01")
			.log("route-01 (timer) {{node.id}} ...");

		from("timer://simpleTimer?period=4000")
			.routeId("route-02")
			.log("route-02 (timer) {{node.id}} ...");

		from("timer://simpleTimer?period=6000")
			.routeId("route-03")
			.log("route-03 (timer) {{node.id}} ...");
	}

}
