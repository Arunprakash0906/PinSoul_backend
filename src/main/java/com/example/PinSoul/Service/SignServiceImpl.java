package com.example.PinSoul.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PinSoul.Model.Category;
import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Model.SignResponse;
import com.example.PinSoul.Exception.SignException;
import com.example.PinSoul.Respository.SignRepository;
import com.example.PinSoul.Service.interfaceservice.SignService;
import com.example.PinSoul.Security.JwtService;

@Service
public class SignServiceImpl implements SignService {
   @Autowired
   private SignRepository signRepository;

   @Autowired
   private JwtService jwtService;

   @Override
   public SignResponse getUsersignup(Sign sign) {
      if (sign == null) {
         throw new SignException("Sign-up details are required");
      }
      if (sign.getUsername() == null || sign.getUsername().isBlank()) {
         throw new SignException("Username is required");
      }
      if (sign.getPassword() == null || sign.getPassword().isBlank()) {
         throw new SignException("Password is required");
      }
      if (sign.getCategory() == null) {
         throw new SignException("Category is required");
      }
      if (sign.getCategory() != Category.USER) {
         throw new SignException("Admin accounts cannot sign up");
      }
      if (signRepository.existsByUsername(sign.getUsername())) {
         throw new SignException("Username already exists");
      }

      Sign savedSign = signRepository.save(sign);
      return new SignResponse(
            "User signed up successfully",
            savedSign.getSignId(),
            savedSign.getUsername(),
            savedSign.getCategory().toString(),
            null);
   }

   @Override
   public SignResponse getUserSignIn(Sign sign) {
      if (sign == null || sign.getUsername() == null || sign.getUsername().isBlank()
            || sign.getPassword() == null || sign.getPassword().isBlank()) {
         throw new SignException("Invalid username or password");
      }

      Sign savedSign = signRepository.findByUsername(sign.getUsername())
            .orElseThrow(() -> new SignException("Invalid username or password"));
      if (!sign.getPassword().equals(savedSign.getPassword())) {
         throw new SignException("Invalid username or password");
      }

      String message = savedSign.getCategory() == Category.ADMIN
            ? "Admin signed in successfully"
            : "User signed in successfully";

      return new SignResponse(
            message,
            savedSign.getSignId(),
            savedSign.getUsername(),
            savedSign.getCategory().toString(),
            jwtService.generateToken(savedSign.getUsername(), savedSign.getCategory().toString()));
   }
}
