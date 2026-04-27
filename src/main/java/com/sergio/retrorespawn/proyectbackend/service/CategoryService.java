package com.sergio.retrorespawn.proyectbackend.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergio.retrorespawn.proyectbackend.dto.CategoryRequestDTO;
import com.sergio.retrorespawn.proyectbackend.dto.CategoryResponseDTO;
import com.sergio.retrorespawn.proyectbackend.model.Category;
import com.sergio.retrorespawn.proyectbackend.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO toResponseDTO(Category category){
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getCreatedAt());
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
        .stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id){
        return toResponseDTO(categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found")));
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        verifyName(categoryRequestDTO.getName());
        var category = new Category(categoryRequestDTO.getName());
        return toResponseDTO(categoryRepository.save(category));
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        return categoryRepository.findById(id).map(c -> {
            verifyName(id, categoryRequestDTO.getName());
            c.setName(categoryRequestDTO.getName());
            return toResponseDTO(categoryRepository.save(c));
        }).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Long id){
        categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
        categoryRepository.deleteById(id);
    }

    private void verifyName(Long id, String name){
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isPresent() && !category.get().getId().equals(id)) {
            throw new RuntimeException("Category already exists");
        }
    }

    private void verifyName(String name){
        if (categoryRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
    }


}
