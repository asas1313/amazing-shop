package com.amazing.shop.dto;

import lombok.Data;
import com.amazing.shop.constraint.FieldMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;

@FieldMatch.List({
        @FieldMatch(
                first = "password",
                second = "confirmPassword",
                message = "error.password.match")
})
@Data
public class CustomerRegistrationModel implements Serializable {

    private Long id;

    @NotBlank(message = "error.field.not.blank")
    private String login;

    @NotBlank(message = "error.field.not.blank")
    private String password;

    @NotBlank(message = "error.field.not.blank")
    private String confirmPassword;

    @Email(message = "error.email")
    @NotBlank(message = "error.field.not.blank")
    private String email;

    private Collection<String> roles;

    @AssertTrue
    private Boolean terms;

}