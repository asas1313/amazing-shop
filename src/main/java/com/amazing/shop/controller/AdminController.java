package com.amazing.shop.controller;

import com.amazing.shop.entity.Category;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.CategoryRepository;
import com.amazing.shop.repository.CustomerRepository;
import com.amazing.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/registrations")
    public String registration(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "registration-list";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories-list";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products-list";
    }
}
