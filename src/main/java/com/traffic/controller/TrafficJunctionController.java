package com.traffic.controller;

import com.traffic.entity.TrafficJunction;
import com.traffic.service.TrafficJunctionService;
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
 * REST Controller for TrafficJunction management
 */
@RestController
@RequestMapping("/api/junctions")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrafficJunctionController {

    @Autowired
    private TrafficJunctionService trafficJunctionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllJunctions() {
        log.info("GET request for all junctions");
        List<TrafficJunction> junctions = trafficJunctionService.getAllJunctions();
        return buildSuccessResponse("Junctions fetched successfully", junctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJunctionById(@PathVariable Long id) {
        log.info("GET request for junction ID: {}", id);
        Optional<TrafficJunction> junction = trafficJunctionService.getJunctionById(id);
        if (junction.isPresent()) {
            return buildSuccessResponse("Junction fetched successfully", junction.get());
        }
        return buildErrorResponse("Junction not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/congested/list")
    public ResponseEntity<Map<String, Object>> getCongestedJunctions() {
        log.info("GET request for congested junctions");
        List<TrafficJunction> junctions = trafficJunctionService.getCongestedJunctions();
        return buildSuccessResponse("Congested junctions fetched", junctions);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createJunction(@RequestBody TrafficJunction junction) {
        log.info("POST request to create junction");
        try {
            TrafficJunction created = trafficJunctionService.createJunction(junction);
            return buildSuccessResponse("Junction created successfully", created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateJunction(
            @PathVariable Long id,
            @RequestBody TrafficJunction junctionDetails) {
        log.info("PUT request to update junction ID: {}", id);
        try {
            TrafficJunction updated = trafficJunctionService.updateJunction(id, junctionDetails);
            return buildSuccessResponse("Junction updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteJunction(@PathVariable Long id) {
        log.info("DELETE request for junction ID: {}", id);
        trafficJunctionService.deleteJunction(id);
        return buildSuccessResponse("Junction deleted successfully", null);
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
