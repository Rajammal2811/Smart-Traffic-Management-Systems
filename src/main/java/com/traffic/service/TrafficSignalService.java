package com.traffic.service;

import com.traffic.entity.TrafficSignal;
import com.traffic.repository.TrafficSignalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Traffic Signal management containing business logic
 */
@Service
@Slf4j
@Transactional
public class TrafficSignalService {

    @Autowired
    private TrafficSignalRepository trafficSignalRepository;

    /**
     * Create a new traffic signal
     */
    public TrafficSignal createSignal(TrafficSignal signal) {
        log.info("Creating new traffic signal: {}", signal.getSignalCode());
        if (trafficSignalRepository.findBySignalCode(signal.getSignalCode()).isPresent()) {
            throw new IllegalArgumentException("Signal with code already exists: " + signal.getSignalCode());
        }
        return trafficSignalRepository.save(signal);
    }

    /**
     * Get signal by ID
     */
    public Optional<TrafficSignal> getSignalById(Long signalId) {
        log.info("Fetching signal with ID: {}", signalId);
        return trafficSignalRepository.findById(signalId);
    }

    /**
     * Get signal by code
     */
    public Optional<TrafficSignal> getSignalByCode(String signalCode) {
        log.info("Fetching signal with code: {}", signalCode);
        return trafficSignalRepository.findBySignalCode(signalCode);
    }

    /**
     * Get all signals
     */
    public List<TrafficSignal> getAllSignals() {
        log.info("Fetching all traffic signals");
        return trafficSignalRepository.findAll();
    }

    /**
     * Get signals by status
     */
    public List<TrafficSignal> getSignalsByStatus(String status) {
        log.info("Fetching signals with status: {}", status);
        TrafficSignal.SignalStatus signalStatus = TrafficSignal.SignalStatus.valueOf(status.toUpperCase());
        return trafficSignalRepository.findByStatus(signalStatus);
    }

    /**
     * Get signals by current color
     */
    public List<TrafficSignal> getSignalsByColor(String color) {
        log.info("Fetching signals with color: {}", color);
        TrafficSignal.SignalColor signalColor = TrafficSignal.SignalColor.valueOf(color.toUpperCase());
        return trafficSignalRepository.findByCurrentColor(signalColor);
    }

    /**
     * Get signals by location
     */
    public List<TrafficSignal> getSignalsByLocation(String location) {
        log.info("Fetching signals in location: {}", location);
        return trafficSignalRepository.findByLocationContaining(location);
    }

    /**
     * Update signal information
     */
    public TrafficSignal updateSignal(Long signalId, TrafficSignal signalDetails) {
        log.info("Updating signal with ID: {}", signalId);
        Optional<TrafficSignal> existingSignal = trafficSignalRepository.findById(signalId);
        if (existingSignal.isPresent()) {
            TrafficSignal signal = existingSignal.get();
            signal.setSignalCode(signalDetails.getSignalCode());
            signal.setLocation(signalDetails.getLocation());
            signal.setLatitude(signalDetails.getLatitude());
            signal.setLongitude(signalDetails.getLongitude());
            signal.setCurrentColor(signalDetails.getCurrentColor());
            signal.setGreenDuration(signalDetails.getGreenDuration());
            signal.setYellowDuration(signalDetails.getYellowDuration());
            signal.setRedDuration(signalDetails.getRedDuration());
            signal.setStatus(signalDetails.getStatus());
            return trafficSignalRepository.save(signal);
        }
        throw new IllegalArgumentException("Signal not found with ID: " + signalId);
    }

    /**
     * Delete signal
     */
    public void deleteSignal(Long signalId) {
        log.info("Deleting signal with ID: {}", signalId);
        if (!trafficSignalRepository.existsById(signalId)) {
            throw new IllegalArgumentException("Signal not found with ID: " + signalId);
        }
        trafficSignalRepository.deleteById(signalId);
    }

    /**
     * Get count of signals by status
     */
    public Long getSignalCountByStatus(String status) {
        log.info("Counting signals with status: {}", status);
        TrafficSignal.SignalStatus signalStatus = TrafficSignal.SignalStatus.valueOf(status.toUpperCase());
        return trafficSignalRepository.countByStatus(signalStatus);
    }

}
