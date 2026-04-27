package com.sergio.retrorespawn.proyectbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.retrorespawn.proyectbackend.dto.CartItemDeleteRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CartItemRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CartItemResponseDTO;
import com.sergio.retrorespawn.proyectbackend.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartItemResponseDTO> addItem(@PathVariable Long userId,@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.addItemToCart(userId, cartItemRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateItem(@PathVariable Long id,@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO){
        return ResponseEntity.ok(cartItemService.updateItem(id, cartItemRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id, @Valid @RequestBody CartItemDeleteRequestDTO cartItemDeleteRequestDTO){
        cartItemService.deleteItem(id, cartItemDeleteRequestDTO);
        return ResponseEntity.noContent().build();
    }


}
