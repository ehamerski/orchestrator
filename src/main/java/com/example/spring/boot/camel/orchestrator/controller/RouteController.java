package com.example.spring.boot.camel.orchestrator.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RouteController {

	@Resource
	private CamelContext camelContext;

	@GetMapping("/routes")
	public ResponseEntity<List<String>> getRoutes() {
		return ResponseEntity.ok(camelContext.getRoutes().stream().map(Route::getId).toList());
	}

	@PutMapping("/routes/{id}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableRoute(@PathVariable("id") String id) throws Exception {
		camelContext.getRouteController().resumeRoute(id);
	}

	@PutMapping("/routes/{id}/disable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableRoute(@PathVariable("id") String id) throws Exception {
		camelContext.getRouteController().suspendRoute(id);
	}

}
