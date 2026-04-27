package com.sergio.retrorespawn.proyectbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergio.retrorespawn.proyectbackend.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, String status);
    List<Cart> findAllByUserIdAndStatus(Long userId, String status);

}
