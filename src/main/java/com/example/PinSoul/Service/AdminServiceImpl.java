package com.example.PinSoul.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PinSoul.Exception.AdminException;
import com.example.PinSoul.Model.Category;
import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Respository.LocatoinRepository;
import com.example.PinSoul.Respository.SignRepository;
import com.example.PinSoul.Service.interfaceservice.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private SignRepository signrepo;

    @Autowired
    private LocatoinRepository locationRepository;

    @Override
    public List<Location> getAllLocations(String username) {
        Sign sign = signrepo.findByUsername(username)
                .orElseThrow(() -> new AdminException("Admin account not found"));

        if (sign.getCategory() != Category.ADMIN) {
            throw new AdminException("Admin access required");
        }

        return locationRepository.findAll();
    }

}
