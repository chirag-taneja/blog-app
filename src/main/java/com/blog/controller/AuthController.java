package com.blog.controller;

import com.blog.dto.LoginDto;
import com.blog.dto.SignUpDto;
import com.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginDto loginDto)
    {
        return authService.login(loginDto);
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public String register(@RequestBody SignUpDto signUpDto)
    {
        return authService.signup(signUpDto);
    }
}
