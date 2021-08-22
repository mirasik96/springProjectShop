package com.example.demo.services;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Brand getBrand(Long id);
    Item addItem(Item item);
}
