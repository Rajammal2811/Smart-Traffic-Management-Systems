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
 * Vehicle Entity representing a vehicle in the traffic management system
 */
@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "Vehicle number cannot be blank")
    private String vehicleNumber;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Vehicle type cannot be blank")
    private String vehicleType;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Owner name cannot be blank")
    private String ownerName;

    @Column(nullable = false)
    @NotNull(message = "Entry time is required")
    private LocalDateTime entryTime;

    @Column
    private LocalDateTime exitTime;

    @Column(length = 150)
    private String currentLocation;

    @Column(nullable = false)
    private Double speed = 0.0;

    @Column(nullable = false)
    private Double latitude = 0.0;

    @Column(nullable = false)
    private Double longitude = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Vehicle category is required")
    private VehicleCategory category = VehicleCategory.NORMAL;

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
        if (entryTime == null) {
            entryTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum VehicleCategory {
        NORMAL, AMBULANCE, FIRE_TRUCK, POLICE
    }

    public boolean isEmergency() {
        return category != VehicleCategory.NORMAL;
    }
}
