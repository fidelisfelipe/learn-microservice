package com.organization.project.course.repository;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonsRepository extends JpaRepository<LessonModel, UUID> {
}
