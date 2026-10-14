package com.example.PinSoul.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    private Double latitude;

    private Double longitude;

    private String address;

    @Enumerated(EnumType.STRING)
    private LocationCategory category;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageContentType;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sign_id", nullable = false)
    private Sign sign;

    public Location(Integer locationId, Double latitude, Double longitude, String address, LocationCategory category,
            byte[] imageData, String imageContentType, LocalDateTime createdAt, Sign sign) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.category = category;
        this.imageData = imageData;
        this.imageContentType = imageContentType;
        this.createdAt = createdAt;
        this.sign = sign;
    }
}
