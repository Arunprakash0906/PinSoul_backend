package com.example.PinSoul.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Model.SignResponse;
import com.example.PinSoul.Service.interfaceservice.SignService;

@RestController
@RequestMapping("/sign")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174" })
public class SignController {
   @Autowired
   private SignService signService;

   @PostMapping("/up")
   public ResponseEntity<SignResponse> UserSignUp(@RequestBody Sign sign) {
      return ResponseEntity.status(HttpStatus.CREATED).body(signService.getUsersignup(sign));
   }

   @PostMapping("/in")
   public ResponseEntity<SignResponse> UserSignIn(@RequestBody Sign sign) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(signService.getUserSignIn(sign));
   }
}
