package com.organization.project.course.services;

import com.organization.project.course.model.CourseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(CourseModel course);

    CourseModel save(CourseModel course);

    Optional<CourseModel> findById(UUID courseId);

    List<CourseModel> findAll();
}
