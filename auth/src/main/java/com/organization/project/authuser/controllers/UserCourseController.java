package com.organization.project.authuser.controllers;

import com.organization.project.authuser.clients.CourseClient;
import com.organization.project.authuser.dto.UserCourseDto;
import com.organization.project.authuser.dtos.CourseDto;
import com.organization.project.authuser.models.UserCourseModel;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.services.UserCourseService;
import com.organization.project.authuser.services.UserService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserService userService;

    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCourseByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)Pageable pageable,
                                                              @PathVariable(value="userId") UUID userId){
        Page<CourseDto> pageResult = Page.empty();

        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
        }catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pageResult);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pageResult);
    }
    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                               @RequestBody @Valid UserCourseDto userCourseDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        if(userCourseService.existsByUserAndCourseId(userModelOptional.get(), userCourseDto.getCourseId())){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("subscription already exists!");
        }

        UserCourseModel userCourseModel = userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseDto.getCourseId()));

       return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);
    }

}
