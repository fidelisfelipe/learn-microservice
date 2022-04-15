package com.organization.project.course.controllers;

import com.organization.project.course.clients.AuthUserClient;
import com.organization.project.course.dtos.SubscriptionDto;
import com.organization.project.course.dtos.UserDto;
import com.organization.project.course.enums.UserStatus;
import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.CourseUserModel;
import com.organization.project.course.services.CourseService;
import com.organization.project.course.services.CourseUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value="courseId") UUID courseId){
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));

    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionsUserInCourse(@PathVariable(value="courseId") UUID courseId,
                                                                @RequestBody @Valid SubscriptionDto subscriptionDto){

        ResponseEntity<UserDto> responseUser;

        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("course not found");

        if(courseUserService.existsByCourseAndUserId(courseOptional.get(), subscriptionDto.getUserId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");

        try {
            responseUser = authUserClient.getOneUserById(subscriptionDto.getUserId());
            if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked");

        }catch (HttpStatusCodeException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User not found");
        }

        CourseUserModel courseUserModel = courseUserService.save(courseOptional.get().convertToCourseUserModel(subscriptionDto.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
    }

}
