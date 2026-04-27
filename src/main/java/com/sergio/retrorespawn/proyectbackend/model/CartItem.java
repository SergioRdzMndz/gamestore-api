package com.sergio.retrorespawn.proyectbackend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public CartItem(){

    }
    public CartItem(Integer quantity, BigDecimal unitPrice, Game game, Cart cart){
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.game = game;
        this.cart = cart;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "id: " + id + " | quantity: " + quantity + " | unit price: " + unitPrice + " | game name: " + (game != null ? game.getName() : "No game") + " | cart id: " + (cart != null ? cart.getId() : "No cart");
    }


}
