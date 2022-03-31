package com.organization.project.course.dtos;

import com.organization.project.course.enums.CourseLevel;
import com.organization.project.course.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class LessonDto {

    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String videoUrl;
}
