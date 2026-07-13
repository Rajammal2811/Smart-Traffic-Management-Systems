package com.traffic.repository;

import com.traffic.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Vehicle entity
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByVehicleType(String vehicleType);

    List<Vehicle> findByOwnerName(String ownerName);

    @Query("SELECT v FROM Vehicle v WHERE v.exitTime IS NULL")
    List<Vehicle> findActiveVehicles();

    @Query("SELECT v FROM Vehicle v WHERE v.entryTime BETWEEN :startTime AND :endTime")
    List<Vehicle> findVehiclesByTimeRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.exitTime IS NULL")
    Long countActiveVehicles();
}
