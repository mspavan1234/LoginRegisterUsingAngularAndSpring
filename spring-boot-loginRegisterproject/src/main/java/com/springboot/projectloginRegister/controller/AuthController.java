package com.springboot.projectloginRegister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.projectloginRegister.dto.LoginRequest;
import com.springboot.projectloginRegister.dto.RegisterRequest;
import com.springboot.projectloginRegister.service.AuthService;
import com.springboot.projectloginRegister.service.AuthenticationResponse;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private AuthService authService;

    @SuppressWarnings("rawtypes")
	@PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
