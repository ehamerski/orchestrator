package com.example.spring.boot.camel.orchestrator;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.file.cluster.FileLockClusterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterNodeConfiguration {

	@Bean
	public RouteBuilder routeBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {				
				// This route is configured to be local (see application.properties)
				// so it will be started regardless of the leadership status if
				// this node.
				from("timer:heartbeat?period=10000")
					.routeId("heartbeat")
					.log("HeartBeat route (timer) {{node.id}} ...");

				from("timer://simpleTimer?period=1000")
					.routeId("route-01")
					.log("route-01 (timer) {{node.id}} ...");                	

				from("timer://simpleTimer?period=2000")
					.routeId("route-02")
					.log("route-02 (timer) {{node.id}} ...");                	

				from("timer://simpleTimer?period=4000")
					.routeId("route-03")
					.log("route-03 (timer) {{node.id}} ...");                	
				
				// This route is configured to be clustered so it will be started
				// by the controller only when this node is leader
				from("master:{{node.namespace}}:timer:clustered?period=5000")
					.routeId("clustered")
					.log("Clustered route (timer) {{node.id}} ...");
			}
		};
	}

}