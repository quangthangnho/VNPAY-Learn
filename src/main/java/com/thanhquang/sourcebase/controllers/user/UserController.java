package com.thanhquang.sourcebase.controllers.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanhquang.sourcebase.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users management", description = "Users management API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user", description = "Get user")
    @GetMapping
    public String getUser() {
        return "hello user ne";
    }
}
