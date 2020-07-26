package com.amazing.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
public class CategoryRegistrationModel {

    private Long id;

    @NotBlank(message = "error.field.not.blank")
    private String name;

    private Collection<String> category;
}
