package com.amazing.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(250)")
    private String title;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String brand;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    @Check(constraints = "available_count >= 0")
    private Double price;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,0)")
    @Check(constraints = "available_count >= 0")
    private Integer quantity;

    //once the table is done this should be done
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category productCategory;

    @OneToMany(mappedBy = "product")
    Set<CartLine> cartLines;
}
