package com.thanhquang.sourcebase.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email", example = "quangvvt.0802@gmail.com", name = "email", type = "string")
    private String email;

    @NotEmpty(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "Password", example = "******", name = "password", type = "string")
    private String password;
}
