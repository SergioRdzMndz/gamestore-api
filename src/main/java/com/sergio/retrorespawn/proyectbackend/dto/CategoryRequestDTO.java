package com.sergio.retrorespawn.proyectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    @Size(min = 3)
    private String name;

    public CategoryRequestDTO(){

    }
    public CategoryRequestDTO(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
