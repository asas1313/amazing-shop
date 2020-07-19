package com.amazing.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(250)")
    private String login;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(250)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(250)")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(
                    name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;

    @Column(columnDefinition = "VARCHAR(250)")
    private String city;

    @Column(columnDefinition = "VARCHAR(250)")
    private String address;

    @Column(nullable = false)
    private int channel;
}
