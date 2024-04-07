package com.example.blog.controller;

import com.example.blog.dto.CategoryDto;
import com.example.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping({"", "/create"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping({"/{id}", "/update/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }

    @DeleteMapping({"/{id}", "/delete/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
