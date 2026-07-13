package com.traffic.repository;

import com.traffic.entity.TrafficSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for TrafficSignal entity
 */
@Repository
public interface TrafficSignalRepository extends JpaRepository<TrafficSignal, Long> {

    Optional<TrafficSignal> findBySignalName(String signalName);

    List<TrafficSignal> findByStatus(TrafficSignal.SignalStatus status);

    List<TrafficSignal> findByLocationContaining(String location);
}
