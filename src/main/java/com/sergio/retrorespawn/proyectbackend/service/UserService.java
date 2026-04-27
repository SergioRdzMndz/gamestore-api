package com.sergio.retrorespawn.proyectbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergio.retrorespawn.proyectbackend.dto.UserRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.UserResponseDTO;
import com.sergio.retrorespawn.proyectbackend.model.Cart;
import com.sergio.retrorespawn.proyectbackend.model.User;
import com.sergio.retrorespawn.proyectbackend.repository.CartRepository;
import com.sergio.retrorespawn.proyectbackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository){
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
        .stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        verifyEmail(userRequestDTO.getEmail());
        User user = new User(
            userRequestDTO.getUsername(),
            userRequestDTO.getEmail(),
            userRequestDTO.getPassword()
        );
        userRepository.save(user);

        Cart cart = new Cart(user);
        cartRepository.save(cart);

        return toResponseDTO(user);
    }

    public UserResponseDTO getUserById(Long id){
        return toResponseDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){
        return userRepository.findById(id).map(u -> {
            u.setUsername(userRequestDTO.getUsername());
            verifyEmail(id,userRequestDTO.getEmail());
            u.setEmail(userRequestDTO.getEmail());
            u.setPassword(userRequestDTO.getPassword());
            return toResponseDTO(userRepository.save(u));
        }).orElseThrow(()-> new RuntimeException("User not found"));

    }

    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }

    private void verifyEmail(Long userId, String email){
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
            throw new RuntimeException("Email already exists");
        }
    }

    private void verifyEmail(String email){
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }
}
