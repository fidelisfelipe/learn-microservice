package com.organization.project.authuser.controllers;

import com.organization.project.authuser.dtos.UserDto;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.models.enums.UserStatus;
import com.organization.project.authuser.models.enums.UserType;
import com.organization.project.authuser.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto){
        if(userService.existsByUsername(userDto.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: username found");

        if(userService.existsByEmail(userDto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: email found");

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
