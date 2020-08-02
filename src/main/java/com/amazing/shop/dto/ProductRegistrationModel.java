package com.amazing.shop.dto;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;

@Data
public class ProductRegistrationModel implements Serializable {

    private Long id;

    @NotBlank(message = "error.field.not.blank")
    private String title;

    @NotBlank(message = "error.field.not.blank")
    private String brand;

    @NotBlank(message = "error.field.not.blank")
    private String description;

    @NotBlank(message = "error.field.not.blank")
    private String thumbnail;

    @NotBlank(message = "error.field.not.blank")
    private Double price;

    @NotBlank(message = "error.field.not.blank")
    private Integer quantity;

    private Collection<String> products;

    @AssertTrue
    private Boolean terms;

}
