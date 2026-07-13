package com.traffic.service;

import com.traffic.entity.Vehicle;
import com.traffic.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Vehicle management
 */
@Service
@Slf4j
@Transactional
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Create a new vehicle
     */
    public Vehicle createVehicle(Vehicle vehicle) {
        log.info("Creating vehicle with number: {}", vehicle.getVehicleNumber());
        if (vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber()).isPresent()) {
            throw new IllegalArgumentException("Vehicle already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    /**
     * Get all vehicles
     */
    public List<Vehicle> getAllVehicles() {
        log.info("Fetching all vehicles");
        return vehicleRepository.findAll();
    }

    /**
     * Get vehicle by ID
     */
    public Optional<Vehicle> getVehicleById(Long id) {
        log.info("Fetching vehicle with ID: {}", id);
        return vehicleRepository.findById(id);
    }

    /**
     * Get active vehicles
     */
    public List<Vehicle> getActiveVehicles() {
        log.info("Fetching active vehicles");
        return vehicleRepository.findActiveVehicles();
    }

    /**
     * Get vehicle count
     */
    public Long getActiveVehicleCount() {
        return vehicleRepository.countActiveVehicles();
    }

    /**
     * Update vehicle
     */
    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        log.info("Updating vehicle with ID: {}", id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            Vehicle v = vehicle.get();
            v.setVehicleType(vehicleDetails.getVehicleType());
            v.setOwnerName(vehicleDetails.getOwnerName());
            v.setCurrentLocation(vehicleDetails.getCurrentLocation());
            v.setSpeed(vehicleDetails.getSpeed());
            v.setLatitude(vehicleDetails.getLatitude());
            v.setLongitude(vehicleDetails.getLongitude());
            return vehicleRepository.save(v);
        }
        throw new IllegalArgumentException("Vehicle not found");
    }

    /**
     * Delete vehicle
     */
    public void deleteVehicle(Long id) {
        log.info("Deleting vehicle with ID: {}", id);
        vehicleRepository.deleteById(id);
    }

    /**
     * Mark vehicle as exited
     */
    public Vehicle exitVehicle(Long id) {
        log.info("Vehicle exiting with ID: {}", id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            Vehicle v = vehicle.get();
            v.setExitTime(LocalDateTime.now());
            return vehicleRepository.save(v);
        }
        throw new IllegalArgumentException("Vehicle not found");
    }
}
