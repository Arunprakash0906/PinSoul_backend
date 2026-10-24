package com.example.PinSoul.Model.Response;

import java.time.LocalDateTime;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;

public record AdminLocationResponse(
        Integer locationId,
        Double latitude,
        Double longitude,
        String address,
        LocationCategory category,
        byte[] imageData,
        String imageContentType,
        LocalDateTime createdAt) {

    public static AdminLocationResponse from(Location location) {
        return new AdminLocationResponse(
                location.getLocationId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAddress(),
                location.getCategory(),
                location.getImageData(),
                location.getImageContentType(),
                location.getCreatedAt());
    }
}
