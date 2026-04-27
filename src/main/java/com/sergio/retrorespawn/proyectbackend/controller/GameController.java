package com.sergio.retrorespawn.proyectbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.retrorespawn.proyectbackend.dto.GameRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.GameResponseDTO;
import com.sergio.retrorespawn.proyectbackend.service.GameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<List<GameResponseDTO>> getAllGames(@RequestParam(required = false) Long categoryId){
        return ResponseEntity.ok(gameService.getAllGames(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> getGameById(@PathVariable Long id){
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PostMapping()
    public ResponseEntity<GameResponseDTO> createGame(@Valid @RequestBody GameRequestDTO gameRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(gameRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> updateGame(@PathVariable Long id,@Valid @RequestBody GameRequestDTO gameRequestDTO){
        return ResponseEntity.ok(gameService.updateGame(id, gameRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id){
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

}
