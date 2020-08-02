package com.amazing.shop.controller;

import com.amazing.shop.entity.Category;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.CategoryRepository;
import com.amazing.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.repository.CustomerRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/deals")
    public String deals (Model model) {
        return "deals";
    }

    @GetMapping("/faqs")
    public String faqs (Model model) {
        return "faqs";
    }

    @GetMapping("/aboutus")
    public String aboutus (Model model) {
        return "aboutus";
    }

    @GetMapping("/allquestions")
    public String allquestions (Model model) {
        return "allquestions";
    }

    @GetMapping("/contactus")
    public String contactus (Model model) {
        return "contactus";
    }

    @GetMapping("/cart")
    public String cart (Model model) {
        return "cart";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
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
