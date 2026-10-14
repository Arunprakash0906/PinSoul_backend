package com.example.PinSoul.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PinSoul.Exception.LocationException;
import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;
import com.example.PinSoul.Respository.LocatoinRepository;
import com.example.PinSoul.Service.interfaceservice.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocatoinRepository locatoinRepository;

    @Override
    public String saveLocation(Location l) {
        // Validate fields before saving to database
        validateLocationFields(l);

        // Set creation timestamp
        l.setCreatedAt(LocalDateTime.now());

        // Save to database
        locatoinRepository.save(l);

        return "Location saved successfully";
    }

    // Validation method
    private void validateLocationFields(Location location) {

        // Validate latitude (must be between -90 and 90)
        if (location.getLatitude() == null) {
            throw new LocationException("Latitude cannot be null");
        }
        if (location.getLatitude() < -90 || location.getLatitude() > 90) {
            throw new LocationException("Latitude must be between -90 and 90");
        }

        // Validate longitude (must be between -180 and 180)
        if (location.getLongitude() == null) {
            throw new LocationException("Longitude cannot be null");
        }
        if (location.getLongitude() < -180 || location.getLongitude() > 180) {
            throw new LocationException("Longitude must be between -180 and 180");
        }

        // Validate address (cannot be null or empty)
        if (location.getAddress() == null || location.getAddress().trim().isEmpty()) {
            throw new LocationException("Address cannot be null or empty");
        }

        // Validate sign (cannot be null)
        if (location.getSign() == null) {
            throw new LocationException("Sign cannot be null");
        }
    }

    @Override
    public List<Location> getAllLocation(Integer signid) {
        // Validate sign ID is not null
        if (signid == null) {
            throw new LocationException("Sign ID cannot be null");
        }

        // Fetch locations by sign ID from database
        List<Location> locations = locatoinRepository.findLocationsBySignId(signid);

        // Return empty list if no locations found (valid response)
        return locations;
    }

    @Override
    public String deleteLocation(Integer locationId) {
        // Validate location ID is not null
        if (locationId == null) {
            throw new LocationException("Location ID cannot be null");
        }

        // Check if location exists
        if (!locatoinRepository.existsById(locationId)) {
            throw new LocationException("Location not found with ID: " + locationId);
        }

        // Delete location from database
        locatoinRepository.deleteById(locationId);

        return "Location deleted successfully";
    }

    @Override
    public List<Location> getLocationByCategory(Integer signid, LocationCategory category) {
        // Validate sign ID is not null
        if (signid == null) {
            throw new LocationException("Sign ID cannot be null");
        }

        // Validate category is not null or empty
        if (category == null) {
            throw new LocationException("Category cannot be null");
        }

        // Fetch locations by sign ID and category from database
        List<Location> locations = locatoinRepository.findLocationsBySignIdAndCategory(signid, category);

        // Return empty list if no locations found (valid response)
        return locations;
    }
}
