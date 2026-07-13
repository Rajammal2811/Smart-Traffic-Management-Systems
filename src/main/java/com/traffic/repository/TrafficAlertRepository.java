package com.traffic.repository;

import com.traffic.entity.TrafficAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for TrafficAlert entity
 */
@Repository
public interface TrafficAlertRepository extends JpaRepository<TrafficAlert, Long> {

    List<TrafficAlert> findByAlertType(TrafficAlert.AlertType alertType);

    List<TrafficAlert> findBySeverity(TrafficAlert.AlertSeverity severity);

    List<TrafficAlert> findByStatus(TrafficAlert.AlertStatus status);

    @Query("SELECT a FROM TrafficAlert a WHERE a.createdTime BETWEEN :startTime AND :endTime ORDER BY a.createdTime DESC")
    List<TrafficAlert> findAlertsByTimeRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT a FROM TrafficAlert a WHERE a.status = 'ACTIVE' ORDER BY a.severity DESC")
    List<TrafficAlert> findActiveAlerts();
}
