package com.thanhquang.sourcebase.controllers.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin management", description = "Admin management API")
public class AdminController {

    @Operation(summary = "Get Admin", description = "Get Admin")
    @GetMapping
    public String getAdmin() {
        return "hello admin ne";
    }
}
