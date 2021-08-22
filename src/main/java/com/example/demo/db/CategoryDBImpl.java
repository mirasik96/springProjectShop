package com.example.demo.db;

import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class CategoryDBImpl implements CategoryDB{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
