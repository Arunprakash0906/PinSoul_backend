package com.example.PinSoul.Service.interfaceservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;

@Service
public interface LocationService {

  String saveLocation(Location l);

  List<Location> getAllLocation(Integer signid);

  String deleteLocation(Integer locationId);

  List<Location> getLocationByCategory(Integer signid, LocationCategory category);

}
