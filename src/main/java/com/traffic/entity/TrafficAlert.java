package com.traffic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * TrafficAlert Entity representing a traffic alert in the system
 */
@Entity
@Table(name = "traffic_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Alert type is required")
    private AlertType alertType;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Column(nullable = false)
    @NotNull(message = "Created time is required")
    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Severity is required")
    private AlertSeverity severity;

    @Column(nullable = false)
    private Double latitude = 0.0;

    @Column(nullable = false)
    private Double longitude = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status is required")
    private AlertStatus status = AlertStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "junction_id")
    private TrafficJunction junction;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum AlertType {
        ACCIDENT, CONGESTION, SIGNAL_FAILURE, WEATHER, OBSTRUCTION, SPEED_VIOLATION, EMERGENCY_VEHICLE
    }

    public enum AlertSeverity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public enum AlertStatus {
        ACTIVE, ACKNOWLEDGED, RESOLVED
    }
}
