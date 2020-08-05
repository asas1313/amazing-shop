package com.amazing.shop.controller;

import com.amazing.shop.dto.CustomerRegistrationModel;
import com.amazing.shop.entity.Category;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.CategoryRepository;
import com.amazing.shop.repository.ProductRepository;
import com.amazing.shop.service.CustomerService;
import com.amazing.shop.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.repository.CustomerRepository;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        CustomerRegistrationModel customer = new CustomerRegistrationModel();
        model.addAttribute("customer", customer);
        return "registration-user";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("customer") @Valid CustomerRegistrationModel registrationModel,
                           BindingResult result) {
        Customer existing = customerService.findByLogin(registrationModel.getLogin()).orElse(null);
        if ((existing != null
                || customerRepository.findByEmail(registrationModel.getEmail()).isPresent())
                && registrationModel.getId() == null) {
            result.rejectValue("login", ""
                    , "There is already an account registered with this login or email");
        }

        if (result.hasErrors()) {
            return "registration-admin";
        }

        customerService.save(registrationModel);
        return "redirect:/registrations?success";
    }

    @GetMapping("/userDetails")
    public String showUpdateForm(Model model, Authentication authentication) {
        CustomerRegistrationModel customerRegistrationModel = new CustomerRegistrationModel();

        if(authentication != null && authentication.isAuthenticated()) {

            String name = authentication.getName();
            Customer customer = customerRepository.findByLogin(name).orElse(null);
            if (customer == null) {
                new RuntimeException("There is no account registered with this username");
            }

            customerRegistrationModel.setId(customer.getId());
            customerRegistrationModel.setLogin(customer.getLogin());
            customerRegistrationModel.setPassword(customer.getPassword());
            customerRegistrationModel.setConfirmPassword(customer.getPassword());
            customerRegistrationModel.setLogin(customer.getLogin());
            customerRegistrationModel.setEmail(customer.getEmail());
            customerRegistrationModel.setIsAdmin(customer.getRole().equals("ROLE_ADMIN"));
            customerRegistrationModel.setCity(customer.getCity());
            customerRegistrationModel.setAddress(customer.getAddress());
            customerRegistrationModel.setEnabled(customer.getEnabled());
        } else {
            new RuntimeException("Please, connect first.");
        }
        model.addAttribute("customer", customerRegistrationModel);
        return "registration-user";
    }

    @GetMapping("/deals")
    public String deals(Model model) {
        return "deals";
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        return "faqs";
    }

    @GetMapping("/aboutus")
    public String aboutus(Model model) {
        return "aboutus";
    }

    @GetMapping("/allquestions")
    public String allquestions (Model model) {
        return "allquestions";
    }

    @GetMapping("/contactus")
    public String contactus(Model model) {
        return "contactus";
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

    @GetMapping("/add-to-cart/{id}/{quantity}")
    public String addToCart(Model model, @PathVariable Long id, @PathVariable int quantity){
        Product existing = productService.findById(id).orElse(null);
        if (existing == null) {
            new RuntimeException("There is no product with this id");
        }
        return "redirect:/cart";
    }

    @GetMapping("/productFilter")
    public String getBrand(@RequestParam String brand, Model model) {
        List<Product> products = productRepository.findByBrand(brand);
        model.addAttribute("products", products);
        return "brands";
    }

}
