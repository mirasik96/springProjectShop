package com.example.demo.config;

import com.example.demo.db.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreConfig {

    @Bean(name = "db")
    public ItemDB getItemDB(){
        ItemDBImpl itemDB = new ItemDBImpl();
        return itemDB;
    }

    @Bean(name = "brand")
    public BrandDB getBrandDB(){
        return new BrandDBImpl();
    }

    @Bean(name = "category")
    public CategoryDB getCategoryDB(){
        return new CategoryDBImpl();
    }
}
