package com.example.PinSoul.Service.interfaceservice;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.example.PinSoul.Model.Sign;
import com.example.PinSoul.Model.SignResponse;

@Service
public interface SignService {

    public SignResponse getUsersignup(Sign sign);

    public SignResponse getUserSignIn(Sign sign);

}
