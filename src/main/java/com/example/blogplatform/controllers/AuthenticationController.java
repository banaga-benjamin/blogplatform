package com.example.blogplatform.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogplatform.models.User;
import com.example.blogplatform.repositories.UserRepository;

import com.example.blogplatform.dtos.AuthRequest;
import com.example.blogplatform.dtos.AuthResponse;

import com.example.blogplatform.exceptions.BadRequestException;
import com.example.blogplatform.exceptions.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;
import com.example.blogplatform.security.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/apis/auth")
public class AuthenticationController {

    private final JWTUtil util;
    private final UserRepository userrepo;
    private final PasswordEncoder encoder;

    public AuthenticationController(UserRepository repo, PasswordEncoder encoder, JWTUtil util) {
        this.util = util;
        this.userrepo = repo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequest request) throws BadRequestException {
        if (userrepo.existsByUsername(request.getUsername( ))) {
            throw new BadRequestException("username already taken");
        }

        User user = new User( );
        user.setUsername(request.getUsername( ));
        user.setPassword(encoder.encode(request.getPassword( )));
        userrepo.save(user);

        return ResponseEntity.ok("user has been registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) throws ResourceNotFoundException {
        User user = userrepo.findByUsername(request.getUsername( )).orElseThrow(
            ( ) -> new ResourceNotFoundException("user does not exist")
        );

        if (!encoder.matches(request.getPassword( ), user.getPassword( ))) {
            throw new ResourceNotFoundException("user does not exist");
        }

        AuthResponse response = new AuthResponse( );
        response.setToken(util.generateToken(request.getUsername( )));
        return ResponseEntity.ok(response);
    }
}
