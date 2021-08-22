package com.example.demo.controllers;

import com.example.demo.db.BrandDB;
import com.example.demo.db.CategoryDB;
import com.example.demo.db.ItemDB;
import com.example.demo.entities.Brand;
import com.example.demo.entities.Category;
import com.example.demo.entities.Item;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    @Qualifier("db")
    private ItemDB itemDB;

    @Autowired
    @Qualifier("brand")
    private BrandDB brandDB;

    @Autowired
    @Qualifier("category")
    private CategoryDB categoryDB;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String homepage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        List<Item> items = itemDB.getItems();
        model.addAttribute("items", items);
        return "homepage";
    }

    @GetMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        List<Item> items = itemDB.getItems();
        List<Brand> brands = brandDB.getBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping(value = "/about")
    public String about(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "about";
    }

    @GetMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItemPage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "additem";
    }

    @PostMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String toAddItem(@RequestParam(name="name") String name,
                            @RequestParam(name="description") String desc,
                            @RequestParam(name="price") double price,
                            @RequestParam(name="amount") int amount,
                            @RequestParam(name="brandId") Long brandId){
        Item item = new Item();
        item.setName(name);
        item.setDescription(desc);
        item.setPrice(price);
        item.setAmount(amount);
        Brand brand = brandDB.getBrand(brandId);
        if(brand != null){
            item.setBrand(brand);
        }
        itemDB.addItem(item);
        return "redirect:/index";
    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model, @PathVariable(name="id") Long id){
        model.addAttribute("CURRENT_USER", getUser());
        Item item = itemDB.getItem(id);
        List<Category> categories = categoryDB.getCategories();
        List<Brand> brands = brandDB.getBrands();
        categories.removeAll(item.getCategories());
        model.addAttribute("item", item);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "details";
    }

    @PostMapping(value = "/details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String toEdit(@RequestParam(name="id") Long id,
                         @RequestParam(name="name") String name,
                         @RequestParam(name="description") String desc,
                         @RequestParam(name="price") double price,
                         @RequestParam(name="amount") int amount,
                         @RequestParam(name="categories") List<Category> categories){
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(desc);
        item.setPrice(price);
        item.setAmount(amount);
        if(categories != null){
            item.setCategories(categories);
        }
        itemDB.updateItem(item);
        return "redirect:/index";
    }

    @GetMapping(value = "/deleteItem/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String toDelete(@PathVariable(name="id") Long id, Model model){
        model.addAttribute("CURRENT_USER", getUser());
        itemDB.deleteItem(id);
        return "redirect:/index";
    }

    @PostMapping(value = "/addCategoryInItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addCategoryInItem(@RequestParam("id") Long id,
                                    @RequestParam("categoryId") Long categoryId){
        Category category = categoryDB.getCategory(categoryId);
        if(category != null){
            Item item = itemDB.getItem(id);
            if(item != null){
                List<Category> categories = item.getCategories();
                if(categories == null){
                    categories = new ArrayList<>();
                }
                if(!categories.contains(category)){
                    categories.add(category);
                }
                item.setCategories(categories);
                itemDB.updateItem(item);
                return "redirect:/details/"+item.getId()+"";
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleteCategoryInItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteCategoryInItem(@RequestParam("id") Long id,
                                    @RequestParam("categoryId") Long categoryId){
        Category category = categoryDB.getCategory(categoryId);
        if(category != null){
            Item item = itemDB.getItem(id);
            if(item != null){
                List<Category> categories = item.getCategories();
                if(categories == null){
                    categories = new ArrayList<>();
                }
                if(categories.contains(category)){
                    categories.remove(category);
                }
                item.setCategories(categories);
                itemDB.updateItem(item);
                return "redirect:/details/"+item.getId()+"";
            }
        }
        return "redirect:/";
    }

    @GetMapping(value="/loginpage")
    public String loginPage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "login";
    }

    @GetMapping(value="/registerpage")
    public String registerPage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "register";
    }

    @GetMapping(value="/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "profile";
    }

    @GetMapping(value="/accessdeniedpage")
    public String accessDeniedPage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "403";
    }

    @PostMapping(value="/toregister")
    public String toRegister(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("repassword") String repassword,
                             @RequestParam("fullname") String fullname){
        if(password.equals(repassword)){
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setFullname(fullname);
            if(userService.addUser(user) != null){
                return "redirect:/registerpage?success";
            }
            return "redirect:/registerpage?emailerror";
        }
        return "redirect:/registerpage?passworderror";
    }

    @PostMapping(value="/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam("oldpassword") String oldpassword,
                                 @RequestParam("newpassword") String newpassword,
                                 @RequestParam("renewpassword") String renewpassword){
        User currentUser = getUser();
        if(newpassword.equals(renewpassword)){
            if(passwordEncoder.matches(oldpassword, currentUser.getPassword())){
                currentUser.setPassword(passwordEncoder.encode(newpassword));
                userService.updateUser(currentUser);
                return "redirect:/profile?passwordsuccess";
            }
            return "redirect:/profile?oldpasserror";
        }
        return "redirect:/profile?passworderror";
    }

    @PostMapping(value="/updateprofile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(@RequestParam("fullname") String fullname){
        User currentUser = getUser();
        currentUser.setFullname(fullname);
        userService.updateUser(currentUser);
        return "redirect:/profile?success";
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }else{
            return null;
        }
    }


}
