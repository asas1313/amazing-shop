package com.amazing.shop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.amazing.shop.dto.CustomerRegistrationModel;
import com.amazing.shop.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService extends UserDetailsService {

    Optional<Customer> findById(Long id);

    Optional<Customer> findByLogin(String login);

    Customer save(CustomerRegistrationModel registrationModel);

    List<Customer> findAll();
}
