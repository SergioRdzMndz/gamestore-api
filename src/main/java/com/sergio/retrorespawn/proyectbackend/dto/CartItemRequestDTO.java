package com.sergio.retrorespawn.proyectbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemRequestDTO {

    @NotNull(message = "Game is required")
    private Long gameId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public CartItemRequestDTO(){

    }

    public CartItemRequestDTO(Long gameId, Integer quantity){
        this.gameId = gameId;
        this.quantity = quantity;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
}
