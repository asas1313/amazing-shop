package com.amazing.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
public class CartLineRegistrationModel {

    @NotBlank
    private Long customerId;

    @NotBlank
    private Long productId;

    @NotBlank
    private Integer quantity;

}
