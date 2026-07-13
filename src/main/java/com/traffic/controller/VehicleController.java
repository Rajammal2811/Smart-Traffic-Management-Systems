package com.traffic.controller;

import com.traffic.entity.Vehicle;
import com.traffic.service.VehicleService;
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
 * REST Controller for Vehicle management
 */
@RestController
@RequestMapping("/api/vehicles")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllVehicles() {
        log.info("GET request for all vehicles");
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return buildSuccessResponse("Vehicles fetched successfully", vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVehicleById(@PathVariable Long id) {
        log.info("GET request for vehicle ID: {}", id);
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            return buildSuccessResponse("Vehicle fetched successfully", vehicle.get());
        }
        return buildErrorResponse("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/active/all")
    public ResponseEntity<Map<String, Object>> getActiveVehicles() {
        log.info("GET request for active vehicles");
        List<Vehicle> vehicles = vehicleService.getActiveVehicles();
        return buildSuccessResponse("Active vehicles fetched", vehicles);
    }

    @GetMapping("/count/active")
    public ResponseEntity<Map<String, Object>> getActiveVehicleCount() {
        log.info("GET request for active vehicle count");
        Long count = vehicleService.getActiveVehicleCount();
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        return buildSuccessResponse("Active vehicle count fetched", data);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createVehicle(@RequestBody Vehicle vehicle) {
        log.info("POST request to create vehicle");
        try {
            Vehicle created = vehicleService.createVehicle(vehicle);
            return buildSuccessResponse("Vehicle created successfully", created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle vehicleDetails) {
        log.info("PUT request to update vehicle ID: {}", id);
        try {
            Vehicle updated = vehicleService.updateVehicle(id, vehicleDetails);
            return buildSuccessResponse("Vehicle updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/exit")
    public ResponseEntity<Map<String, Object>> exitVehicle(@PathVariable Long id) {
        log.info("PUT request to mark vehicle as exited: {}", id);
        try {
            Vehicle updated = vehicleService.exitVehicle(id);
            return buildSuccessResponse("Vehicle exited successfully", updated);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable Long id) {
        log.info("DELETE request for vehicle ID: {}", id);
        vehicleService.deleteVehicle(id);
        return buildSuccessResponse("Vehicle deleted successfully", null);
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
