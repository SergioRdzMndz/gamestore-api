package com.sergio.retrorespawn.proyectbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.retrorespawn.proyectbackend.dto.CartResponseDTO;
import com.sergio.retrorespawn.proyectbackend.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("/{userId}/my-cart")
    public ResponseEntity<CartResponseDTO> showCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/{userId}/checkout")
    public ResponseEntity<CartResponseDTO> confirmPurchase(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.checkout(userId));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<CartResponseDTO>> showHistory(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getCartHistory(userId));
    }

}
