package com.amazing.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.amazing.shop.dto.CustomerRegistrationModel;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.repository.CustomerRepository;
import com.amazing.shop.service.CustomerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @ModelAttribute("customer")
    public CustomerRegistrationModel customerRegistration() {
        return new CustomerRegistrationModel();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
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
            return "registration";
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        registrationModel.setRoles(roles);

        customerService.save(registrationModel);
        return "redirect:/registration?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        Customer existing = customerService.findById(id).orElse(null);
        if (existing == null) {
            new RuntimeException("There is no account registered with this id");
        }
        customerRepository.delete(existing);
        return "redirect:/registration-list?deleted";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            new RuntimeException("There is no account registered with this id");
        }
        CustomerRegistrationModel customerRegistrationModel = new CustomerRegistrationModel();
        customerRegistrationModel.setLogin(customer.getLogin());
        customerRegistrationModel.setEmail(customer.getEmail());
        customerRegistrationModel.setId(customer.getId());
        model.addAttribute("customer", customerRegistrationModel);
        return "registration";
    }

}