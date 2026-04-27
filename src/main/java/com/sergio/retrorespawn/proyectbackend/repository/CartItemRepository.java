package com.sergio.retrorespawn.proyectbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.retrorespawn.proyectbackend.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    Optional<CartItem> findByCartIdAndGameId(Long cartId, Long gameId);

}
