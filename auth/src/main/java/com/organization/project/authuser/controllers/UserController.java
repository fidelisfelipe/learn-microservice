package com.organization.project.authuser.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> user = userService.findById(userId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> user = userService.findById(userId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        userService.delete(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }
}
