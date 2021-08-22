package com.example.demo.db;

import com.example.demo.entities.Brand;

import java.util.List;

public interface BrandDB {
    public List<Brand> getBrands();

    public Brand getBrand(Long id);

    public void addBrand(Brand brand);

    public void updateBrand(Brand brand);

    public void deleteBrand(Long id);
}
