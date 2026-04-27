package com.sergio.retrorespawn.proyectbackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GameResponseDTO {

   private Long id;
   private String name;
   private String description;
   private BigDecimal price;
   private String imageUrl;
   private Integer stock;
   private String categoryName;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
   private LocalDateTime createdAt;

   public GameResponseDTO(){

   }

   public GameResponseDTO(Long id, String name, String description, BigDecimal price, String imageUrl, Integer stock, String categoryName, LocalDateTime createdAt){
    this.id = id;
    this.name = name;
    this. description = description;
    this.price = price;
    this.imageUrl = imageUrl;
    this.stock = stock;
    this.categoryName = categoryName;
    this.createdAt = createdAt;
   }

   public Long getId() {
    return id;
   }

   public void setId(Long id) {
    this.id = id;
   }

   public String getName() {
    return name;
   }

   public void setName(String name) {
    this.name = name;
   }

   public String getDescription() {
    return description;
   }

   public void setDescription(String description) {
    this.description = description;
   }

   public BigDecimal getPrice() {
    return price;
   }

   public void setPrice(BigDecimal price) {
    this.price = price;
   }

   public String getImageUrl() {
    return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
   }

   public Integer getStock() {
    return stock;
   }

   public void setStock(Integer stock) {
    this.stock = stock;
   }

   public String getCategoryName() {
    return categoryName;
   }

   public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
   }

   public LocalDateTime getCreatedAt() {
    return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
   }

   

}
