package com.thanhquang.sourcebase.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public ResponseEntity<String> error() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Custom 404 Page - Page Not Found");
    }
}
