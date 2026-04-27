package com.sergio.retrorespawn.proyectbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergio.retrorespawn.proyectbackend.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{
    Optional<Game> findByName(String name);
    List<Game> findAllByCategoryId(Long categoryId);

}
