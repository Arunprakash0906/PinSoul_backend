package com.example.PinSoul.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Service.interfaceservice.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class AdminDashboardController {

    @Autowired
    private AdminService adminser;

    @GetMapping("/all-pin-loactions")
    public ResponseEntity<List<Location>> getAllLocations(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminser.getAllLocations(authentication.getName()));
    }

}
