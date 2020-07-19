package com.amazing.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.amazing.shop.dto.CustomerRegistrationModel;
import com.amazing.shop.entity.Customer;
import com.amazing.shop.entity.Role;
import com.amazing.shop.repository.CustomerRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByLogin(String login) {

        return customerRepository.findByLogin(login);
    }

    @Override
    public Customer save(CustomerRegistrationModel registrationModel) {
        Customer customer = new Customer();
        customer.setId(registrationModel.getId());
        customer.setLogin(registrationModel.getLogin());
        customer.setEmail(registrationModel.getEmail());
        customer.setPassword(passwordEncoder.encode(registrationModel.getPassword()));
        customer.setRoles(remapRoles(registrationModel.getRoles()));
        return customerRepository.save(customer);
    }

    private Collection<Role> remapRoles(Collection<String> roles) {
        return roles.stream()
                .map(Role::new)
                .collect(toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLogin(login).orElse(null);
        if (customer == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(customer.getLogin(),
                customer.getPassword(),
                mapRolesToAuthorities(customer.getRoles()));
    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }
}
