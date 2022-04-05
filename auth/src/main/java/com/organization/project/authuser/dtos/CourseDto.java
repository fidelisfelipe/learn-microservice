package com.organization.project.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.organization.project.authuser.enums.CourseLevel;
import com.organization.project.authuser.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLevel;
}
