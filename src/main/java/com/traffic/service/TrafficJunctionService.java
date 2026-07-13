package com.traffic.service;

import com.traffic.entity.TrafficJunction;
import com.traffic.repository.TrafficJunctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for TrafficJunction management
 */
@Service
@Slf4j
@Transactional
public class TrafficJunctionService {

    @Autowired
    private TrafficJunctionRepository trafficJunctionRepository;

    /**
     * Create a new junction
     */
    public TrafficJunction createJunction(TrafficJunction junction) {
        log.info("Creating junction: {}", junction.getJunctionName());
        if (trafficJunctionRepository.findByJunctionName(junction.getJunctionName()).isPresent()) {
            throw new IllegalArgumentException("Junction already exists");
        }
        return trafficJunctionRepository.save(junction);
    }

    /**
     * Get all junctions
     */
    public List<TrafficJunction> getAllJunctions() {
        log.info("Fetching all junctions");
        return trafficJunctionRepository.findAll();
    }

    /**
     * Get junction by ID
     */
    public Optional<TrafficJunction> getJunctionById(Long id) {
        log.info("Fetching junction with ID: {}", id);
        return trafficJunctionRepository.findById(id);
    }

    /**
     * Update junction
     */
    public TrafficJunction updateJunction(Long id, TrafficJunction junctionDetails) {
        log.info("Updating junction with ID: {}", id);
        Optional<TrafficJunction> junction = trafficJunctionRepository.findById(id);
        if (junction.isPresent()) {
            TrafficJunction j = junction.get();
            j.setTrafficDensity(junctionDetails.getTrafficDensity());
            j.setStatus(junctionDetails.getStatus());
            j.setVehicleCount(junctionDetails.getVehicleCount());
            return trafficJunctionRepository.save(j);
        }
        throw new IllegalArgumentException("Junction not found");
    }

    /**
     * Delete junction
     */
    public void deleteJunction(Long id) {
        log.info("Deleting junction with ID: {}", id);
        trafficJunctionRepository.deleteById(id);
    }

    /**
     * Get congested junctions
     */
    public List<TrafficJunction> getCongestedJunctions() {
        log.info("Fetching congested junctions");
        return trafficJunctionRepository.findCongestedJunctions(60);
    }
}
