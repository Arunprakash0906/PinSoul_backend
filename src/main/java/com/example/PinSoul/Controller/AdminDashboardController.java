package com.example.PinSoul.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.Response.AdminLocationResponse;
import com.example.PinSoul.Service.interfaceservice.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    @Autowired
    private AdminService adminser;

    @GetMapping({ "/all-pin-locations", "/all-pin-loactions" })
    public ResponseEntity<List<AdminLocationResponse>> getAllLocations(Authentication authentication) {
        List<AdminLocationResponse> locations = adminser.getAllLocations(authentication.getName())
                .stream()
                .map(AdminLocationResponse::from)
                .toList();
        return ResponseEntity.ok(locations);
    }

}
