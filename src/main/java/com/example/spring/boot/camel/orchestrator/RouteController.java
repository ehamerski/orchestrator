package com.example.spring.boot.camel.orchestrator;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

	@Resource
	private CamelContext camelContext;
	
	@GetMapping("/routes")
	public ResponseEntity<List<String>> getRoutes() {
		return ResponseEntity.ok(camelContext.getRoutes().stream().map(Route::getId).collect(Collectors.toList()));		
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
