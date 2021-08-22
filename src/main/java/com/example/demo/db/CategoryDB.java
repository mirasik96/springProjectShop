package com.example.demo.db;

import com.example.demo.entities.Category;

import java.util.List;

public interface CategoryDB {
    public List<Category> getCategories();

    public Category getCategory(Long id);

    public void addCategory(Category category);

    public void updateCategory(Category category);

    public void deleteCategory(Long id);
}
