package com.organization.project.course.controllers;

import com.organization.project.course.clients.CourseClient;
import com.organization.project.course.dtos.SubscriptionDto;
import com.organization.project.course.dtos.UserDto;
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

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value="courseId") UUID courseId){
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllUsersByCourse(courseId, pageable));

    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionsUserInCourse(@PathVariable(value="courseId") UUID courseId,
                                                                @RequestBody @Valid SubscriptionDto subscriptionDto){

        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("course not found");

        if(courseUserService.existsByCourseAndUserId(courseOptional.get(), subscriptionDto.getUserId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");

        //verificação user
        CourseUserModel courseUserModel = courseUserService.save(courseOptional.get().convertToCourseUserModel(subscriptionDto.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }

}
