package com.organization.project.course.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseUserDto {
    private UUID courseId;
    private UUID userId;
}
