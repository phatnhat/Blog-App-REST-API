package com.example.blog.service;

import com.example.blog.dto.CategoryDto;
import com.example.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    void deleteCategory(Long id);
}
