package com.traffic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Signal name cannot be blank")
    private String signalName;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignalStatus status = SignalStatus.RED;

    @Column(nullable = false)
    @Min(value = 1, message = "Timer must be at least 1 second")
    private Integer timer = 30; // in seconds

    @Column(nullable = false)
    private Double latitude = 0.0;

    @Column(nullable = false)
    private Double longitude = 0.0;

    @Column(nullable = false)
    @Min(value = 5, message = "Green duration must be at least 5 seconds")
    private Integer greenDuration = 30;

    @Column(nullable = false)
    @Min(value = 1, message = "Yellow duration must be at least 1 second")
    private Integer yellowDuration = 5;

    @Column(nullable = false)
    @Min(value = 5, message = "Red duration must be at least 5 seconds")
    private Integer redDuration = 30;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "junction_id", nullable = false)
    private TrafficJunction junction;

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
