package com.sergio.retrorespawn.proyectbackend.service;

import org.springframework.stereotype.Service;

import com.sergio.retrorespawn.proyectbackend.dto.CartItemDeleteRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CartItemRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CartItemResponseDTO;
import com.sergio.retrorespawn.proyectbackend.model.CartItem;
import com.sergio.retrorespawn.proyectbackend.repository.CartItemRepository;
import com.sergio.retrorespawn.proyectbackend.repository.CartRepository;
import com.sergio.retrorespawn.proyectbackend.repository.GameRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final GameRepository gameRepository;
    private final CartRepository cartRepository;

    public CartItemService(CartItemRepository cartItemRepository, GameRepository gameRepository, CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.gameRepository = gameRepository;
        this.cartRepository = cartRepository;
    }

    public CartItemResponseDTO toResponseDTO(CartItem cartItem){
        return new CartItemResponseDTO(
            cartItem.getId(),
            cartItem.getGame().getId(),
            cartItem.getGame().getName(),
            cartItem.getGame().getImageUrl(),
            cartItem.getQuantity(),
            cartItem.getUnitPrice()
        );
    }

    public CartItemResponseDTO addItemToCart(Long userId, CartItemRequestDTO cartItemRequestDTO){
        var game = gameRepository.findById(cartItemRequestDTO.getGameId()).orElseThrow(()-> new RuntimeException("Game not found"));
        var cart = cartRepository.findByUserIdAndStatus(userId, "ACTIVE").orElseThrow(()-> new RuntimeException("Cart not found"));

        var existing = cartItemRepository.findByCartIdAndGameId(cart.getId(), game.getId());

        if (cartItemRequestDTO.getQuantity() > game.getStock()) {
            throw new RuntimeException("Insufficient stock");
        }

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + cartItemRequestDTO.getQuantity());
            game.setStock(game.getStock() - cartItemRequestDTO.getQuantity());
            gameRepository.save(game);
            return toResponseDTO(cartItemRepository.save(existing.get()));
        }
        var item = new CartItem(cartItemRequestDTO.getQuantity(), game.getPrice() , game, cart);
        game.setStock(game.getStock() - cartItemRequestDTO.getQuantity());
        gameRepository.save(game);
        return toResponseDTO(cartItemRepository.save(item));
    }

    public CartItemResponseDTO updateItem(Long id, CartItemRequestDTO cartItemRequestDTO){
        var item = cartItemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));

        var quantity = item.getQuantity();
        var game = item.getGame();
        item.setQuantity(cartItemRequestDTO.getQuantity());

        if (cartItemRequestDTO.getQuantity() > quantity && cartItemRequestDTO.getQuantity() - quantity > game.getStock()) {
            throw new RuntimeException("Insufficient stock");
        }

        if (item.getQuantity() > quantity) {
            game.setStock(game.getStock() - (item.getQuantity() - quantity));
            gameRepository.save(game);
        } else {
            game.setStock(game.getStock() + (quantity - item.getQuantity()));
            gameRepository.save(game);
        }

        return toResponseDTO(cartItemRepository.save(item));
    }

    public void deleteItem(Long id, CartItemDeleteRequestDTO cartItemDeleteRequestDTO){
        var item = cartItemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));

        var game = item.getGame();

        game.setStock(game.getStock() + cartItemDeleteRequestDTO.getQuantity());
        gameRepository.save(game);

        if (cartItemDeleteRequestDTO.getQuantity() < item.getQuantity()) {
            item.setQuantity(item.getQuantity() - cartItemDeleteRequestDTO.getQuantity());
            cartItemRepository.save(item);
            return;
        }

        cartItemRepository.deleteById(id);
        
    }

}
