package com.organization.project.authuser.controllers;

import com.organization.project.authuser.dtos.InstructorDto;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.models.enums.UserType;
import com.organization.project.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto dto){
        Optional<UserModel> userModelOptional = userService.findById(dto.getUserId());
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        var userModel = userModelOptional.get();

        BeanUtils.copyProperties(dto, userModel);

        userModel.setUserType(UserType.INSTRUCTOR);

        userService.updateUser(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }
}
