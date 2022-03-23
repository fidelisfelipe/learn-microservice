package com.organization.project.authuser.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.organization.project.authuser.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.services.UserService;

import javax.validation.Valid;

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

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable(value = "userId") UUID userId,
                                         @RequestBody
                                         @Validated(UserDto.UserView.UserPut.class)
                                         @JsonView(UserDto.UserView.UserPut.class) UserDto userDto){
        Optional<UserModel> user = userService.findById(userId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        var userUpdate = user.get();
        userUpdate.setFullName(userDto.getFullName());
        userUpdate.setPhoneNumber(userDto.getPhoneNumber());
        userUpdate.setCpf(userDto.getCpf());

        userService.save(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody
                                                 @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto){

        Optional<UserModel> user = userService.findById(userId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        if(!user.get().getPassword().equals(userDto.getOldPassword()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("mismatched old password");

        var userUpdate = user.get();
        userUpdate.setPassword(userDto.getPassword());

        userService.save(user.get());
        return ResponseEntity.status(HttpStatus.OK).body("password update success");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImg(@PathVariable(value = "userId") UUID userId,
                                            @RequestBody @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto){
        Optional<UserModel> user = userService.findById(userId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        var userUpdate = user.get();
        userUpdate.setImageURL(userDto.getImageURL());

        userService.save(user.get());
        return ResponseEntity.status(HttpStatus.OK).body("image update success");
    }
}
