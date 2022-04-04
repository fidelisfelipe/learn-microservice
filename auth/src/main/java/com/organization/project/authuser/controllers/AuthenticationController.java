package com.organization.project.authuser.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.organization.project.authuser.dtos.UserDto;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.models.enums.UserStatus;
import com.organization.project.authuser.models.enums.UserType;
import com.organization.project.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                                   @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto){
        log.debug("POST registerUser userDto received {} ", userDto);

        if(userService.existsByUsername(userDto.getUsername())) {
            log.warn("Username {} is Already Taken ", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: username found");
        }

        if(userService.existsByEmail(userDto.getEmail())) {
            log.warn("Email {} is Already Taken ", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: email found");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userService.save(userModel);
        log.info("POST registerUser userId saved {} ", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @GetMapping("/logtest")
    public void logger(){
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
    }
}
