package com.organization.project.authuser.repositories;

import com.organization.project.authuser.models.UserCourseModel;
import com.organization.project.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
}
