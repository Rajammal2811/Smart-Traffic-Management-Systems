package com.traffic.repository;

import com.traffic.entity.TrafficJunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for TrafficJunction entity
 */
@Repository
public interface TrafficJunctionRepository extends JpaRepository<TrafficJunction, Long> {

    Optional<TrafficJunction> findByJunctionName(String junctionName);

    List<TrafficJunction> findByStatus(TrafficJunction.JunctionStatus status);

    List<TrafficJunction> findByLocationContaining(String location);

    @Query("SELECT j FROM TrafficJunction j WHERE j.trafficDensity > :threshold ORDER BY j.trafficDensity DESC")
    List<TrafficJunction> findCongestedJunctions(Integer threshold);
}
