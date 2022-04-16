package com.organization.project.authuser.services;

import com.organization.project.authuser.models.UserCourseModel;
import com.organization.project.authuser.models.UserModel;

import java.util.UUID;

public interface UserCourseService {
    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);
    UserCourseModel save(UserCourseModel convertToUserCourseModel);

    boolean existsByCourseId(UUID courseId);

    void deleteUserCourseByCourse(UUID courseId);
}
