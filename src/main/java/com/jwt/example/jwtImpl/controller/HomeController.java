package com.jwt.example.jwtImpl.controller;

import com.jwt.example.jwtImpl.model.AuthRequest;
import com.jwt.example.jwtImpl.model.User;
import com.jwt.example.jwtImpl.service.JwtService;
import com.jwt.example.jwtImpl.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class HomeController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    //    Logger logger = (Logger) LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }else {
            throw new UsernameNotFoundException("User name not Found!");
        }

    }



}