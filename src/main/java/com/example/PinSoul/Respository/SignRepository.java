package com.example.PinSoul.Respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PinSoul.Model.Sign;

@Repository
public interface SignRepository extends JpaRepository<Sign, Integer> {
    boolean existsByUsername(String username);

    Optional<Sign> findByUsername(String username);
}
