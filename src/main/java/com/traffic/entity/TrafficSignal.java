package com.traffic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * TrafficSignal Entity representing a traffic signal in the system
 */
@Entity
@Table(name = "traffic_signals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficSignal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String signalName;

    @Column(nullable = false, length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignalStatus status;

    @Column(nullable = false)
    private Integer timer = 30; // in seconds

    @Column(nullable = false)
    private Double latitude = 0.0;

    @Column(nullable = false)
    private Double longitude = 0.0;

    @Column(nullable = false)
    private Integer greenDuration = 30;

    @Column(nullable = false)
    private Integer yellowDuration = 5;

    @Column(nullable = false)
    private Integer redDuration = 30;

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

    public enum SignalStatus {
        RED, YELLOW, GREEN, OFFLINE
    }
}
