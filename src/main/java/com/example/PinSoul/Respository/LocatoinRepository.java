package com.example.PinSoul.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PinSoul.Model.Location;
import com.example.PinSoul.Model.LocationCategory;

@Repository
public interface LocatoinRepository extends JpaRepository<Location, Integer> {

    // Fetch locations by sign ID
    @Query("SELECT l FROM Location l WHERE l.sign.signId = :signId")
    List<Location> findLocationsBySignId(@Param("signId") Integer signId);

    @Query("SELECT l FROM Location l WHERE l.sign.signId = :signId AND l.category = :category")
    List<Location> findLocationsBySignIdAndCategory(@Param("signId") Integer signId,
            @Param("category") LocationCategory category);

}
