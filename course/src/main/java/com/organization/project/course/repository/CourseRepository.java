package com.organization.project.course.repository;

import com.organization.project.course.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> , JpaSpecificationExecutor<CourseModel> {
}
