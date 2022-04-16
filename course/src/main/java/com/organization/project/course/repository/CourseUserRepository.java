package com.organization.project.course.repository;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID>, JpaSpecificationExecutor<CourseUserModel> {

    boolean existsByCourseAndUserId(CourseModel courseUser, UUID userId);

    //TODO: not work in query native
    //@Query(value = "select * from tb_courses_users where course_course_id = :courseId", nativeQuery = true)
    @Query(value = "SELECT courseUserModel from CourseUserModel courseUserModel where courseUserModel.course.courseId = :courseId ")
    List<CourseUserModel> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);
}
