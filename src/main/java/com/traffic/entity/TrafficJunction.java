package com.traffic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * TrafficJunction Entity representing a traffic junction in the system
 */
@Entity
@Table(name = "traffic_junctions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficJunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String junctionName;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false)
    private Integer numberOfSignals;

    @Column(nullable = false)
    private Integer trafficDensity; // percentage 0-100

    @Column(nullable = false)
    private Double latitude = 0.0;

    @Column(nullable = false)
    private Double longitude = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JunctionStatus status;

    @Column(nullable = false)
    private Integer vehicleCount = 0;

    @Column(nullable = false)
    private Integer capacity = 100; // max vehicles

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum JunctionStatus {
        NORMAL, CONGESTED, HEAVY_TRAFFIC, CLEAR
    }
}
