package com.organization.project.course.validation;

import com.organization.project.course.clients.AuthUserClient;
import com.organization.project.course.dtos.CourseDto;
import com.organization.project.course.dtos.UserDto;
import com.organization.project.course.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;


@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    AuthUserClient authUserClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;
        if(!errors.hasErrors()){
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        ResponseEntity<UserDto> userDtoResponseEntity;
        try {
            userDtoResponseEntity = authUserClient.getOneUserById(userInstructor);
            if(userDtoResponseEntity.getBody().getUserType().equals(UserType.STUDENT)){
                errors.rejectValue("userInstructor","UserInstructorError","User must be INSTRUCTOR OR ADMIN.");
            }
        }catch (HttpStatusCodeException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                errors.rejectValue("userInstructor","UserInstructorError","Instructor not found");
            }
        }
    }


}
