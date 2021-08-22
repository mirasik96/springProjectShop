package com.example.demo.repositories;

import com.example.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
