package com.example.authservice.controllers;

import com.example.authservice.models.ResponseAuthenticationModel;
import com.example.authservice.models.ResponseAuthorizationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface AuthController {
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseAuthenticationModel login(@RequestParam String username, @RequestParam String password);

    @GetMapping("/authorities")
    @ResponseStatus(HttpStatus.OK)
    ResponseAuthorizationModel getAuthorities(@RequestParam String token);
}
