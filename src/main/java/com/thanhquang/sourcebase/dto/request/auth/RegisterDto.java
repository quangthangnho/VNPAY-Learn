package com.thanhquang.sourcebase.dto.request.auth;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto implements Serializable {

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email", example = "quangvvt.0802@gmail.com", name = "email", type = "string")
    private String email;

    @NotEmpty(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "Password", example = "******", name = "password", type = "string")
    private String password;

    @NotEmpty(message = "FullName is required")
    @Schema(description = "FullName", example = "Quang Vo", name = "fullName", type = "string")
    private String fullName;

    @NotEmpty(message = "Phone number is required")
    @Schema(description = "Phone number", example = "0123456789", name = "phoneNumber", type = "string")
    private String phoneNumber;
}
