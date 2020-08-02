package com.amazing.shop.controller;

import com.amazing.shop.dto.CartLineRegistrationModel;
import com.amazing.shop.dto.CustomerRegistrationModel;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.ProductRepository;
import com.amazing.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/cart")
    public String showCart(@RequestParam Long customerId, Model model) {
        List<CartLineRegistrationModel> cart = customerService.convertCartEntityToModel(customerService.findById(customerId).get().getCartLines());
        model.addAttribute("cart", cart);
        return "cart";
    }
}
