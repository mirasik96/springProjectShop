package com.example.demo.services;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Item;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }
}
