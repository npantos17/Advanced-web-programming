package com.example.dom3.controller;

import com.example.dom3.models.Permission;
import com.example.dom3.models.User;
import com.example.dom3.models.UserUpdateReq;
import com.example.dom3.services.UserService;
import com.example.dom3.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping(value = "/get/permissions")
    private ResponseEntity<List<Permission>> getRoles(){
        return ResponseEntity.ok().body(userService.getAllPermissions());
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user){
        return userService.newUser(user);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<User> updateUser(@RequestBody UserUpdateReq user){
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id.intValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
