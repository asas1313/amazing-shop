package com.amazing.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CartLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @MapsId("customer_id")
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;

    Integer quantity;
}
