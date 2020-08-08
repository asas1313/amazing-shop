package com.amazing.shop.dto;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "error.field.not.null")
    private Double price;

    @NotNull(message = "error.field.not.null")
    private Integer quantity;

    private Collection<String> products;

    @AssertTrue
    private Boolean terms;

}
