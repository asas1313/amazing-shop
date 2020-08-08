package com.amazing.shop.service;

import com.amazing.shop.dto.CartLineRegistrationModel;
import com.amazing.shop.entity.CartLine;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private ProductService productService;

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
        customer.setRoles(singletonList(new Role((registrationModel.getIsAdmin())?"ROLE_ADMIN":"ROLE_USER")));
        customer.setEnabled(registrationModel.getEnabled());
        customer.setCity(registrationModel.getCity());
        customer.setAddress(registrationModel.getAddress());
        customer.setCartLines(convertCartLineModelToEntity(registrationModel.getCartLines()));
        return customerRepository.save(customer);
    }

    public List<CartLine> convertCartLineModelToEntity (List<CartLineRegistrationModel> registrationModel) {
        List<CartLine> returnCartLineList = new ArrayList<>();

        if(!registrationModel.isEmpty()) {
            registrationModel.forEach(regModel -> {
                CartLine line = new CartLine();
                line.setCustomer(findById(regModel.getCustomerId()).get());
                line.setProduct(productService.findById(regModel.getProductId()).get());
                line.setQuantity(regModel.getQuantity());
                returnCartLineList.add(line);
            });
        }
        return returnCartLineList;
    }

    public List<CartLineRegistrationModel> convertCartEntityToModel ( List<CartLine> cartLines) {
        List<CartLineRegistrationModel> returnModel = new ArrayList<>();

        cartLines.forEach(line -> {
            CartLineRegistrationModel retModel = new CartLineRegistrationModel();
            retModel.setCustomerId(line.getCustomer().getId());
            retModel.setProductId(line.getProduct().getId());
            retModel.setQuantity(line.getQuantity());
            returnModel.add(retModel);
        });
        return returnModel;
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

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }
}
