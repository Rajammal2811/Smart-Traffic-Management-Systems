package com.traffic.controller;

import com.traffic.entity.TrafficAlert;
import com.traffic.service.TrafficAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for TrafficAlert management
 */
@RestController
@RequestMapping("/api/alerts")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrafficAlertController {

    @Autowired
    private TrafficAlertService trafficAlertService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAlerts() {
        log.info("GET request for all alerts");
        List<TrafficAlert> alerts = trafficAlertService.getAllAlerts();
        return buildSuccessResponse("Alerts fetched successfully", alerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAlertById(@PathVariable Long id) {
        log.info("GET request for alert ID: {}", id);
        Optional<TrafficAlert> alert = trafficAlertService.getAlertById(id);
        if (alert.isPresent()) {
            return buildSuccessResponse("Alert fetched successfully", alert.get());
        }
        return buildErrorResponse("Alert not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/active/list")
    public ResponseEntity<Map<String, Object>> getActiveAlerts() {
        log.info("GET request for active alerts");
        List<TrafficAlert> alerts = trafficAlertService.getActiveAlerts();
        return buildSuccessResponse("Active alerts fetched", alerts);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAlert(@RequestBody TrafficAlert alert) {
        log.info("POST request to create alert");
        TrafficAlert created = trafficAlertService.createAlert(alert);
        return buildSuccessResponse("Alert created successfully", created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAlert(
            @PathVariable Long id,
            @RequestBody TrafficAlert alertDetails) {
        log.info("PUT request to update alert ID: {}", id);
        try {
            TrafficAlert updated = trafficAlertService.updateAlert(id, alertDetails);
            return buildSuccessResponse("Alert updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAlert(@PathVariable Long id) {
        log.info("DELETE request for alert ID: {}", id);
        trafficAlertService.deleteAlert(id);
        return buildSuccessResponse("Alert deleted successfully", null);
    }

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(String message, Object data) {
        return buildSuccessResponse(message, data, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(
            String message, Object data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, status);
    }
}
