package com.example.PinSoul;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.PinSoul.Model.Category;
import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;
import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Respository.LocatoinRepository;
import com.example.PinSoul.Respository.SignRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class LocationImagePersistenceTests {
    @Autowired
    private LocatoinRepository locationRepository;

    @Autowired
    private SignRepository signRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void imageDataRoundTripsAsBinaryData() {
        byte[] imageData = new byte[] { 0, 1, 2, (byte) 0x80, (byte) 0xff };
        Sign sign = signRepository
                .save(new Sign(null, "image-test-" + UUID.randomUUID(), "password", Category.USER));
        Location location = new Location();
        location.setLatitude(12.5);
        location.setLongitude(45.5);
        location.setAddress("Image persistence test");
        location.setCategory(LocationCategory.OTHER);
        location.setImageData(imageData);
        location.setSign(sign);

        Location savedLocation = locationRepository.saveAndFlush(location);
        Integer locationId = savedLocation.getLocationId();
        entityManager.clear();

        Location reloadedLocation = locationRepository.findById(locationId).orElseThrow();

        assertArrayEquals(imageData, reloadedLocation.getImageData());
    }
}
