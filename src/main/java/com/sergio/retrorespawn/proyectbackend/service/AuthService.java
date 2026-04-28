package com.sergio.retrorespawn.proyectbackend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.sergio.retrorespawn.proyectbackend.dto.LoginRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.LoginResponseDTO;
import com.sergio.retrorespawn.proyectbackend.dto.UserRequestDTO;
import com.sergio.retrorespawn.proyectbackend.repository.UserRepository;
import com.sergio.retrorespawn.proyectbackend.security.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
            )
        );

        var user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        var token = jwtService.generateToken(user.getEmail());  

        return new LoginResponseDTO(token);
    }

    public LoginResponseDTO register(UserRequestDTO userRequestDTO){
        var newUser = userService.createUser(userRequestDTO);
        var token = jwtService.generateToken(newUser.getEmail());

        return new LoginResponseDTO(token);
    }
}
