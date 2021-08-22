package com.example.demo.db;

import com.example.demo.entities.Brand;
import com.example.demo.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BrandDBImpl implements BrandDB{
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public void addBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
