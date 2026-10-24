package com.example.PinSoul.Service.interfaceservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.PinSoul.Model.Location;

@Service
public interface AdminService {

    List<Location> getAllLocations(String username);

}
