package com.amazing.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;


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

    @Column(nullable = false, columnDefinition = "VARCHAR(250) default 'ROLE_USER'")
    private String role;

    @OneToMany(mappedBy = "customer")
    List<CartLine> cartLines;

}
