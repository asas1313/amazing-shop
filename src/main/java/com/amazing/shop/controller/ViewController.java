package com.amazing.shop.controller;

import com.amazing.shop.entity.Category;
import com.amazing.shop.repository.CategoryRepository;
import com.amazing.shop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.repository.CustomerRepository;
import com.amazing.shop.service.CustomerServiceImpl;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Customer> users = new CustomerServiceImpl(customerRepository, passwordEncoder).findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/registration-list")
    public String registration(Model model) {
        List<Customer> users = new CustomerServiceImpl(customerRepository, passwordEncoder).findAll();
        model.addAttribute("users", users);
        return "registration-list";
    }
    @GetMapping("/categories-list")
    public String categories(Model model) {
        List<Category> categories = new CategoryService(categoryRepository).findAll();
        model.addAttribute("categories", categories);
        return "categories-list";
    }

}
