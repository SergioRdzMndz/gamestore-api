package com.sergio.retrorespawn.proyectbackend.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.sergio.retrorespawn.proyectbackend.dto.GameRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.GameResponseDTO;
import com.sergio.retrorespawn.proyectbackend.model.Category;
import com.sergio.retrorespawn.proyectbackend.model.Game;
import com.sergio.retrorespawn.proyectbackend.repository.CategoryRepository;
import com.sergio.retrorespawn.proyectbackend.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;

    public GameService(GameRepository gameRepository, CategoryRepository categoryRepository){
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
    }

    public GameResponseDTO toResponseDTO(Game game){
        return new GameResponseDTO(
            game.getId(),
            game.getName(),
            game.getDescription(),
            game.getPrice(),
            game.getImageUrl(),
            game.getStock(),
            game.getCategory().getName(),
            game.getCreatedAt()
        );
    }

    public GameResponseDTO createGame(GameRequestDTO gameRequestDTO){
        verifyGameName(gameRequestDTO.getName());

        Category category = categoryRepository.findById(gameRequestDTO.getCategoryId())
        .orElseThrow(()-> new RuntimeException("Category not found"));

        Game game = new Game(
            gameRequestDTO.getName(),
            gameRequestDTO.getDescription(),
            gameRequestDTO.getPrice(),
            gameRequestDTO.getImageUrl(),
            gameRequestDTO.getStock(),
            category);

        return toResponseDTO(gameRepository.save(game));
    }

    public List<GameResponseDTO> getAllGames(Long categoryId){

        List<Game> games;

        if (categoryId != null) {
            games = gameRepository.findAllByCategoryId(categoryId);
        } else {
            games = gameRepository.findAll();
        }
        return games.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public GameResponseDTO getGameById(Long id){
        return toResponseDTO(gameRepository.findById(id).orElseThrow(()-> new RuntimeException("Game not found")));
    }

    public GameResponseDTO updateGame(Long id, GameRequestDTO gameRequestDTO){
        return gameRepository.findById(id).map(g -> {
            verifyGameName(id, gameRequestDTO.getName());
            g.setName(gameRequestDTO.getName());
            g.setDescription(gameRequestDTO.getDescription());
            g.setPrice(gameRequestDTO.getPrice());
            g.setImageUrl(gameRequestDTO.getImageUrl());
            g.setStock(gameRequestDTO.getStock());
            Category category = categoryRepository.findById(gameRequestDTO.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));
            g.setCategory(category);
            return toResponseDTO(gameRepository.save(g));
        }).orElseThrow(()-> new RuntimeException("Game not found"));
    }

    public void deleteGame(Long id){
        gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        gameRepository.deleteById(id);
    }
       

    private void verifyGameName(String name){
        if (gameRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Game already exists");
        }
    }
    private void verifyGameName(Long id, String name){
        Optional<Game> existingGame = gameRepository.findByName(name);

        if (existingGame.isPresent() && !existingGame.get().getId().equals(id)) {
            throw new RuntimeException("Game already exists");
        }
    }
}
