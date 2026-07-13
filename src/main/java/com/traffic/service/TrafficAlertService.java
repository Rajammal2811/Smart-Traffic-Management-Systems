package com.traffic.service;

import com.traffic.entity.TrafficAlert;
import com.traffic.repository.TrafficAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for TrafficAlert management
 */
@Service
@Slf4j
@Transactional
public class TrafficAlertService {

    @Autowired
    private TrafficAlertRepository trafficAlertRepository;

    /**
     * Create a new alert
     */
    public TrafficAlert createAlert(TrafficAlert alert) {
        log.info("Creating alert: {}", alert.getAlertType());
        return trafficAlertRepository.save(alert);
    }

    /**
     * Get all alerts
     */
    public List<TrafficAlert> getAllAlerts() {
        log.info("Fetching all alerts");
        return trafficAlertRepository.findAll();
    }

    /**
     * Get alert by ID
     */
    public Optional<TrafficAlert> getAlertById(Long id) {
        log.info("Fetching alert with ID: {}", id);
        return trafficAlertRepository.findById(id);
    }

    /**
     * Get active alerts
     */
    public List<TrafficAlert> getActiveAlerts() {
        log.info("Fetching active alerts");
        return trafficAlertRepository.findActiveAlerts();
    }

    /**
     * Update alert
     */
    public TrafficAlert updateAlert(Long id, TrafficAlert alertDetails) {
        log.info("Updating alert with ID: {}", id);
        Optional<TrafficAlert> alert = trafficAlertRepository.findById(id);
        if (alert.isPresent()) {
            TrafficAlert a = alert.get();
            a.setStatus(alertDetails.getStatus());
            return trafficAlertRepository.save(a);
        }
        throw new IllegalArgumentException("Alert not found");
    }

    /**
     * Delete alert
     */
    public void deleteAlert(Long id) {
        log.info("Deleting alert with ID: {}", id);
        trafficAlertRepository.deleteById(id);
    }
}
