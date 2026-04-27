package com.sergio.retrorespawn.proyectbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergio.retrorespawn.proyectbackend.dto.CartItemResponseDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CartResponseDTO;
import com.sergio.retrorespawn.proyectbackend.model.Cart;
import com.sergio.retrorespawn.proyectbackend.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    public CartResponseDTO toResponseDTO(Cart cart){
        List<CartItemResponseDTO> items = cart.getItems()
        .stream()
        .map(item -> new CartItemResponseDTO(
            item.getId(),
            item.getGame().getId(),
            item.getGame().getName(),
            item.getGame().getImageUrl(),   
            item.getQuantity(),
            item.getUnitPrice()
        )).collect(Collectors.toList());

        return new CartResponseDTO(cart.getId(), cart.getStatus(), items);
    }

    public CartResponseDTO getCart(Long userId){
        Cart cart = cartRepository.findByUserIdAndStatus(userId, "ACTIVE").orElseThrow(()-> new RuntimeException("Cart not found"));
        return toResponseDTO(cart);
    }

    public CartResponseDTO checkout(Long userId){
        Cart cart = cartRepository.findByUserIdAndStatus(userId, "ACTIVE").orElseThrow(()-> new RuntimeException("Cart not found")); // finds the cart whose status I want to change

        cart.setStatus("COMPLETED"); // changes the status
        cartRepository.save(cart); // saves the changes 

        Cart newCart = new Cart(cart.getUser()); // creates a new cart in the db with the same user
        cartRepository.save(newCart); // saves the changes

        return toResponseDTO(cart);
    }

    public List<CartResponseDTO> getCartHistory(Long userId){
        return cartRepository.findAllByUserIdAndStatus(userId,"COMPLETED").stream().map(this::toResponseDTO).collect(Collectors.toList());

    }
    
}
