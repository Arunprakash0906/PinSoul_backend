package com.example.PinSoul.Model.Response;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;
import com.example.PinSoul.Model.Sign;

class AdminLocationResponseTests {
    @Test
    void mapsLocationFieldsWithoutIncludingRelatedAccount() {
        byte[] imageData = new byte[] { 1, 2, 3 };
        Location location = new Location();
        location.setLocationId(7);
        location.setLatitude(12.5);
        location.setLongitude(45.5);
        location.setAddress("Test location");
        location.setCategory(LocationCategory.CAFE);
        location.setImageData(imageData);
        location.setImageContentType("image/jpeg");
        location.setSign(new Sign(2, "test-user", "secret-password", null));

        AdminLocationResponse response = AdminLocationResponse.from(location);

        assertEquals(7, response.locationId());
        assertEquals(12.5, response.latitude());
        assertEquals(45.5, response.longitude());
        assertEquals("Test location", response.address());
        assertEquals(LocationCategory.CAFE, response.category());
        assertArrayEquals(imageData, response.imageData());
        assertEquals("image/jpeg", response.imageContentType());
    }
}
