package com.blog.service;

import com.blog.dao.RoleRepo;
import com.blog.dao.UserRepo;
import com.blog.dto.LoginDto;
import com.blog.dto.SignUpDto;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    UserRepo userRepo;
    RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    


    @Autowired
    public AuthService(UserRepo userRepo, RoleRepo roleRepo, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder=passwordEncoder;

    }

    public String login(LoginDto loginDto)
    {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.userName(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);
        return token;
    }

    public String signup(SignUpDto signUpDto)
    {
        if (userRepo.existsByEmail(signUpDto.email()))
        {
            throw new RuntimeException("User Already Present with this mail");
        }
        if (userRepo.existsByUserName(signUpDto.userName()))
        {
            throw new RuntimeException("User Already Present with this UserName");
        }

        User user= User.builder().userName(signUpDto.userName()).name(signUpDto.name()).password(passwordEncoder.encode(signUpDto.password())).email(signUpDto.email()).build();
        Role role=roleRepo.findByName("user").get();

        user.setRoles(Set.of(role));

        userRepo.save(user);

        return "User Created SuccessFully";
    }


}
