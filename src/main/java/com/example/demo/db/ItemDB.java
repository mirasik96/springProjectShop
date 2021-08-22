package com.example.demo.db;

import com.example.demo.entities.Item;

import java.util.List;

public interface ItemDB {
    public List<Item> getItems();

    public Item getItem(Long id);

    public void addItem(Item item);

    public void updateItem(Item item);

    public void deleteItem(Long id);
}
