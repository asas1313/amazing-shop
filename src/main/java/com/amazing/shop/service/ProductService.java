package com.amazing.shop.service;

import com.amazing.shop.dto.ProductRegistrationModel;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    public Optional<Product> findByTitle(String title) {

        return productRepository.findByTitle(title);
    }

    public List<Product> findByBrand(String brand) {

        return productRepository.findByBrand(brand);
    }


    public Product save(ProductRegistrationModel registrationModel) {
        Product product = new Product();
        product.setId(registrationModel.getId());
        product.setTitle(registrationModel.getTitle());
        product.setBrand(registrationModel.getBrand());
        product.setDescription(registrationModel.getDescription());
        product.setThumbnail(registrationModel.getThumbnail());
        product.setPrice(registrationModel.getPrice());
        product.setQuantity(registrationModel.getQuantity());
     //   product.setProducts(remapProducts(registrationModel.getProducts()));
        return productRepository.save(product);
    }

   /* private Collection<Product> remapProducts(Collection<String> products) {
        return products.stream()
                .map(Products::new)
                .collect(toSet());
    }*/

    public Product loadProductByTitle(String title) throws NotFoundException {
        Product product = productRepository.findByTitle(title).orElse(null);
        if (product == null) {
            throw new NotFoundException ("Product not found.");
        }
        return product;
    }

    public List<Product> findAll() {

        return productRepository.findAll();
    }
}
