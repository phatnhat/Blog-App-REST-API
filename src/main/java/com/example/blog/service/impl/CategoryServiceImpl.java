package com.example.blog.service.impl;

import com.example.blog.dto.CategoryDto;
import com.example.blog.entity.Category;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", id));

        categoryRepository.delete(category);
    }
}
