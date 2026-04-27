package com.sergio.retrorespawn.proyectbackend.dto;

import java.math.BigDecimal;
import java.util.List;


public class CartResponseDTO {
    
    private Long id;
    private String status;
    private List<CartItemResponseDTO> items;
    private BigDecimal total;

    public CartResponseDTO(){

    }
    public CartResponseDTO(Long id, String status, List<CartItemResponseDTO> items){
        this.id = id;
        this.status = status;
        this.items = items;
        this.total = items.stream().map(item -> item.getSubtotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<CartItemResponseDTO> getItems() {
        return items;
    }
    public void setItems(List<CartItemResponseDTO> items) {
        this.items = items;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    

}
