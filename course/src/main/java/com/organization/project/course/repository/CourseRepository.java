package com.organization.project.course.repository;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> , JpaSpecificationExecutor<CourseModel> {

    @Query(value = "select case when count(*) > 0 THEN true ELSE false END FROM tb_courses_users tcu WHERE tcu.course_id = :courseId and tcu.user_id = :userId", nativeQuery = true )
    BigInteger exitsByCourseAndUser(@Param("courseId") UUID courseId,
                                    @Param("userId") UUID userId);

    @Modifying
    @Query(value = "insert into tb_courses_users (user_id, course_id) values (:userId, :courseId)", nativeQuery = true)
    void saveSubscriptionUserInCourse(@Param("userId") UUID userId, @Param("courseId") UUID courseId);

    @Modifying
    @Query(value = "delete from tb_courses_users where user_id =:userId", nativeQuery = true)
    void deleteCourseUserByUser(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "delete from tb_courses_users where courseId =:courseId", nativeQuery = true)
    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);
}
