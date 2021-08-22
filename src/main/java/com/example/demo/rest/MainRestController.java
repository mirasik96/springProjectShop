package com.example.demo.rest;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Item;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class MainRestController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/allitems")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(value = "/additem")
    public ResponseEntity<String> addItem(@RequestParam(name = "name") String name,
                                          @RequestParam(name = "description") String desc,
                                          @RequestParam(name = "price") double price,
                                          @RequestParam(name = "amount") int amount,
                                          @RequestParam(name = "brand_id") Long brandId){
        Brand brand = itemService.getBrand(brandId);

        if(brand!=null){
            Item item = new Item();
            item.setName(name);
            item.setPrice(price);
            item.setAmount(amount);
            item.setDescription(desc);
            item.setBrand(brand);
            itemService.addItem(item);
            return new ResponseEntity<>("ADDED", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR", HttpStatus.OK);
    }
}
