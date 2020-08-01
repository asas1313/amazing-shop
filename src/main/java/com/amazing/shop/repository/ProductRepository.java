package com.amazing.shop.repository;

import com.amazing.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Optional<Product> findByTitle(String title);

    List<Product> findByBrand(String brand);

    List<Product> findAll();

}
