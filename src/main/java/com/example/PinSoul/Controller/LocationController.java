package com.example.PinSoul.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;
import com.example.PinSoul.Service.interfaceservice.LocationService;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins ="*")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/categories")
    public ResponseEntity<LocationCategory[]> getLocationCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(LocationCategory.values());
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> SaveLocation(@RequestBody Location l) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(locationService.saveLocation(l));
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity<String> saveLocationWithImage(
            @RequestPart("location") Location location,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                location.setImageData(image.getBytes());
                location.setImageContentType(image.getContentType());
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(locationService.saveLocation(location));
        } catch (java.io.IOException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to read image");
        }
    }

    @GetMapping("/getlocation/{signid}")
    public ResponseEntity<List<Location>> getLocationAll(@PathVariable Integer signid) {
        return ResponseEntity.status(HttpStatus.OK).body(locationService.getAllLocation(signid));

    }

    @DeleteMapping("/delete/{locationid}")
    public ResponseEntity<String> deleteLocation(@PathVariable Integer locationid) {
        return ResponseEntity.status(HttpStatus.OK).body(locationService.deleteLocation(locationid));
    }

    @GetMapping("/getlocationcategory/{signid}/{category}")
    public ResponseEntity<List<Location>> getLocationByCategory(@PathVariable Integer signid,
            @PathVariable LocationCategory category) {
        return ResponseEntity.status(HttpStatus.OK).body(locationService.getLocationByCategory(signid, category));
    }
}
