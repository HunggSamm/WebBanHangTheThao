package org.example.test.service;

import org.example.test.model.Category;
import org.example.test.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByName(String name) {
        return this.categoryRepository.findCategoryByName(name);
    }

    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Long id) {
        return this.categoryRepository.findCategoryById(id);
    }

    public List<Category> getCategoriesByName(String name) {
        return this.categoryRepository.findCategoriesByName(name);
    }

    public void deleteAllProduct() {
        this.categoryRepository.deleteAll();
    }
}
