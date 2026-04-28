package com.sergio.retrorespawn.proyectbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.retrorespawn.proyectbackend.dto.LoginRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.LoginResponseDTO;
import com.sergio.retrorespawn.proyectbackend.dto.UserRequestDTO;
import com.sergio.retrorespawn.proyectbackend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO ){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userRequestDTO));
    }
}
