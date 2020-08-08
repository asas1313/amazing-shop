package com.amazing.shop.controller;

import com.amazing.shop.entity.CartLine;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class DisplayCartController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{cart}")
    public String cart(Model model, @PathVariable("customerId") Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            new RuntimeException("There is no account registered with this id");
        }
        List<CartLine> cartLines = customer.getCartLines();
        model.addAttribute("cartLines", cartLines);
        return "cart";
    }
}
