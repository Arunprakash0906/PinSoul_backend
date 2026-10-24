package com.example.PinSoul.Respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {
    Optional<User> findBySign(Sign sign);
}
