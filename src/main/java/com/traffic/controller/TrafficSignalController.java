package com.traffic.controller;

import com.traffic.entity.TrafficSignal;
import com.traffic.service.TrafficSignalService;
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
 * REST Controller for TrafficSignal management
 */
@RestController
@RequestMapping("/api/signals")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrafficSignalController {

    @Autowired
    private TrafficSignalService trafficSignalService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSignals() {
        log.info("GET request for all signals");
        List<TrafficSignal> signals = trafficSignalService.getAllSignals();
        return buildSuccessResponse("Signals fetched successfully", signals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSignalById(@PathVariable Long id) {
        log.info("GET request for signal ID: {}", id);
        Optional<TrafficSignal> signal = trafficSignalService.getSignalById(id);
        if (signal.isPresent()) {
            return buildSuccessResponse("Signal fetched successfully", signal.get());
        }
        return buildErrorResponse("Signal not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSignal(@RequestBody TrafficSignal signal) {
        log.info("POST request to create signal");
        try {
            TrafficSignal created = trafficSignalService.createSignal(signal);
            return buildSuccessResponse("Signal created successfully", created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSignal(
            @PathVariable Long id,
            @RequestBody TrafficSignal signalDetails) {
        log.info("PUT request to update signal ID: {}", id);
        try {
            TrafficSignal updated = trafficSignalService.updateSignal(id, signalDetails);
            return buildSuccessResponse("Signal updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSignal(@PathVariable Long id) {
        log.info("DELETE request for signal ID: {}", id);
        trafficSignalService.deleteSignal(id);
        return buildSuccessResponse("Signal deleted successfully", null);
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
