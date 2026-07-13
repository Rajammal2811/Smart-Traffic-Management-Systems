package com.traffic.service;

import com.traffic.dto.VehicleDTO;
import com.traffic.entity.Vehicle;
import com.traffic.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for Vehicle management
 */
@Service
@Slf4j
@Transactional
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Create a new vehicle record
     */
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        log.info("Creating new vehicle with license plate: {}", vehicleDTO.getLicensePlate());
        Vehicle vehicle = vehicleDTO.toEntity();
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return VehicleDTO.fromEntity(savedVehicle);
    }

    /**
     * Get vehicle by ID
     */
    public VehicleDTO getVehicleById(Long vehicleId) {
        log.info("Fetching vehicle with ID: {}", vehicleId);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        return vehicle.map(VehicleDTO::fromEntity).orElse(null);
    }

    /**
     * Get vehicle by license plate
     */
    public VehicleDTO getVehicleByLicensePlate(String licensePlate) {
        log.info("Fetching vehicle with license plate: {}", licensePlate);
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        return vehicle.map(VehicleDTO::fromEntity).orElse(null);
    }

    /**
     * Get all vehicles
     */
    public List<VehicleDTO> getAllVehicles() {
        log.info("Fetching all vehicles");
        return vehicleRepository.findAll().stream()
                .map(VehicleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get vehicles by status
     */
    public List<VehicleDTO> getVehiclesByStatus(String status) {
        log.info("Fetching vehicles with status: {}", status);
        Vehicle.VehicleStatus vehicleStatus = Vehicle.VehicleStatus.valueOf(status);
        return vehicleRepository.findByStatus(vehicleStatus).stream()
                .map(VehicleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get vehicles in a geographic area
     */
    public List<VehicleDTO> getVehiclesInArea(Double minLat, Double maxLat, Double minLon, Double maxLon) {
        log.info("Fetching vehicles in area: [{}, {}] x [{}, {}]", minLat, maxLat, minLon, maxLon);
        return vehicleRepository.findVehiclesInArea(minLat, maxLat, minLon, maxLon).stream()
                .map(VehicleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get vehicles exceeding speed limit
     */
    public List<VehicleDTO> getVehiclesExceedingSpeed(Double speedLimit) {
        log.info("Fetching vehicles exceeding speed: {}", speedLimit);
        return vehicleRepository.findVehiclesExceedingSpeed(speedLimit).stream()
                .map(VehicleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Update vehicle information
     */
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO) {
        log.info("Updating vehicle with ID: {}", vehicleId);
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicleId);
        if (existingVehicle.isPresent()) {
            Vehicle vehicle = existingVehicle.get();
            vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
            vehicle.setVehicleType(vehicleDTO.getVehicleType());
            vehicle.setColor(vehicleDTO.getColor());
            vehicle.setLatitude(vehicleDTO.getLatitude());
            vehicle.setLongitude(vehicleDTO.getLongitude());
            vehicle.setSpeed(vehicleDTO.getSpeed());
            vehicle.setDirection(vehicleDTO.getDirection());
            vehicle.setStatus(Vehicle.VehicleStatus.valueOf(vehicleDTO.getStatus()));
            Vehicle updatedVehicle = vehicleRepository.save(vehicle);
            return VehicleDTO.fromEntity(updatedVehicle);
        }
        return null;
    }

    /**
     * Delete vehicle by ID
     */
    public void deleteVehicle(Long vehicleId) {
        log.info("Deleting vehicle with ID: {}", vehicleId);
        vehicleRepository.deleteById(vehicleId);
    }

    /**
     * Get count of vehicles by status
     */
    public Long getVehicleCountByStatus(String status) {
        log.info("Counting vehicles with status: {}", status);
        Vehicle.VehicleStatus vehicleStatus = Vehicle.VehicleStatus.valueOf(status);
        return vehicleRepository.countByStatus(vehicleStatus);
    }

    /**
     * Get vehicles by date range
     */
    public List<VehicleDTO> getVehiclesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching vehicles created between {} and {}", startDate, endDate);
        return vehicleRepository.findVehiclesByDateRange(startDate, endDate).stream()
                .map(VehicleDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
