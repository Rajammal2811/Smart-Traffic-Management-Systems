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
 * Service class for TrafficSignal management
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
        log.info("Creating traffic signal: {}", signal.getSignalName());
        if (trafficSignalRepository.findBySignalName(signal.getSignalName()).isPresent()) {
            throw new IllegalArgumentException("Signal already exists");
        }
        return trafficSignalRepository.save(signal);
    }

    /**
     * Get all signals
     */
    public List<TrafficSignal> getAllSignals() {
        log.info("Fetching all traffic signals");
        return trafficSignalRepository.findAll();
    }

    /**
     * Get signal by ID
     */
    public Optional<TrafficSignal> getSignalById(Long id) {
        log.info("Fetching signal with ID: {}", id);
        return trafficSignalRepository.findById(id);
    }

    /**
     * Update signal
     */
    public TrafficSignal updateSignal(Long id, TrafficSignal signalDetails) {
        log.info("Updating signal with ID: {}", id);
        Optional<TrafficSignal> signal = trafficSignalRepository.findById(id);
        if (signal.isPresent()) {
            TrafficSignal s = signal.get();
            s.setStatus(signalDetails.getStatus());
            s.setTimer(signalDetails.getTimer());
            s.setGreenDuration(signalDetails.getGreenDuration());
            s.setYellowDuration(signalDetails.getYellowDuration());
            s.setRedDuration(signalDetails.getRedDuration());
            return trafficSignalRepository.save(s);
        }
        throw new IllegalArgumentException("Signal not found");
    }

    /**
     * Delete signal
     */
    public void deleteSignal(Long id) {
        log.info("Deleting signal with ID: {}", id);
        trafficSignalRepository.deleteById(id);
    }

    /**
     * Get signals by status
     */
    public List<TrafficSignal> getSignalsByStatus(TrafficSignal.SignalStatus status) {
        log.info("Fetching signals with status: {}", status);
        return trafficSignalRepository.findByStatus(status);
    }
}
