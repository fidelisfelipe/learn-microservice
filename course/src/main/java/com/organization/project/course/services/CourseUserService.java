package com.organization.project.course.services;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel save(CourseUserModel convertToCourseUserModel);
}
