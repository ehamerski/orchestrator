package com.example.spring.boot.camel.orchestrator.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/routes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteController {

	@Resource
	private CamelContext camelContext;

	@GetMapping
	public ResponseEntity<List<String>> getRoutes() {
		return ResponseEntity.ok(camelContext.getRoutes().stream().map(Route::getId).toList());
	}

	@GetMapping(value = "/{id}/status")
	public ResponseEntity<Map<String,String>> getStatus(@PathVariable("id") String id) {
		validate(id);
		return ResponseEntity.ok(Map.of("id", id, "status", camelContext.getRouteController().getRouteStatus(id).name()));
	}

	@PutMapping(value = "/{id}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enable(@PathVariable("id") String id) throws Exception {
		validate(id);
		camelContext.getRouteController().resumeRoute(id);
	}

	@PutMapping(value = "/{id}/disable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disable(@PathVariable("id") String id) throws Exception {
		validate(id);
		camelContext.getRouteController().suspendRoute(id);
	}

	private void validate(String id) {
		if (camelContext.getRoute(id) == null) {
			throw new InvalidRouteIdException("id not found: " + id);
		}
	}

}
