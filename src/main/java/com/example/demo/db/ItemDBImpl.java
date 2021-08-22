package com.example.demo.db;

import com.example.demo.entities.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDBImpl implements ItemDB{

    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items = repository.findAll();
        return items;
    }

    @Override
    public Item getItem(Long id) {
        Optional<Item> optional = repository.findById(id);
        return  optional.orElse(null);
    }

    @Override
    public void addItem(Item item) {
        repository.save(item);
    }

    @Override
    public void updateItem(Item item) {
        Item itemC = getItem(item.getId());
        if(itemC != null){
            repository.save(item);
        }
    }

    @Override
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
}
