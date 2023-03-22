package com.example.dom3.controller;

import com.example.dom3.requests.LoginRequest;
import com.example.dom3.responses.LoginResponse;
import com.example.dom3.services.UserService;
import com.example.dom3.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        System.out.println(jwtUtil.generateToken(loginRequest.getEmail()));
        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getEmail()), userService.getPermissionsForUser(loginRequest.getEmail())));
    }
}
