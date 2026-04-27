package com.sergio.retrorespawn.proyectbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDeleteRequestDTO {
    @NotNull
    @Min(value = 1)
    private Integer quantity;

    public CartItemDeleteRequestDTO(){

    }

    public CartItemDeleteRequestDTO(Integer quantity){
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
