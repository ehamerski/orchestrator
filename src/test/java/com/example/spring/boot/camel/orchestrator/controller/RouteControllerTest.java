package com.example.spring.boot.camel.orchestrator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {
    public static final String ROUTE_LIST = "[\"master-route-01\",\"master-route-02\",\"master-route-03\",\"route-01\",\"route-02\",\"route-03\"]";
    public static final String ROUTE_01_STATUS_STARTED = "{\"id\":\"route-01\",\"status\":\"Started\"}";
    public static final String ROUTE_01_STATUS_SUSPENDED = "{\"id\":\"route-01\",\"status\":\"Suspended\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRoutes() throws Exception {
        this.mockMvc.perform(get("/routes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(ROUTE_LIST));
    }

    @Test
    void getStatus() throws Exception {
        disableRoute("route-01");
        expectedValidStatusResponse("route-01", ROUTE_01_STATUS_SUSPENDED);
        enableRoute("route-01");
        expectedValidStatusResponse("route-01", ROUTE_01_STATUS_STARTED);
    }

    @Test
    void getStatusWithInvalidId() throws Exception {
        this.mockMvc.perform(get("/routes/{id}/status", "route-xx"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRouteIdException))
                .andExpect(result -> assertEquals("id not found: route-xx", requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void enable() throws Exception {
        enableRoute("route-01");
        expectedValidStatusResponse("route-01", ROUTE_01_STATUS_STARTED);
    }

    @Test
    void enableWithInvalidRouteId() throws Exception {
        this.mockMvc.perform(put("/routes/{id}/enable", "route-xx"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRouteIdException))
                .andExpect(result -> assertEquals("id not found: route-xx", requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void disable() throws Exception {
        disableRoute("route-01");
        expectedValidStatusResponse("route-01", ROUTE_01_STATUS_SUSPENDED);
    }

    @Test
    void disableWithInvalidRouteId() throws Exception {
        this.mockMvc.perform(put("/routes/{id}/disable", "route-xx"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRouteIdException))
                .andExpect(result -> assertEquals("id not found: route-xx", requireNonNull(result.getResolvedException()).getMessage()));
    }


    private void enableRoute(String id) throws Exception {
        this.mockMvc.perform(put("/routes/{id}/enable", id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(emptyOrNullString()));
    }

    private void disableRoute(String id) throws Exception {
        this.mockMvc.perform(put("/routes/{id}/disable", id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(emptyOrNullString()));
    }

    private void expectedValidStatusResponse(String id, String routeStatusPayload) throws Exception {
        this.mockMvc.perform(get("/routes/{id}/status", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(routeStatusPayload));
    }

}