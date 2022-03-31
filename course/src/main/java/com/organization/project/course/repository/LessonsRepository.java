package com.organization.project.course.repository;

import com.organization.project.course.model.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LessonsRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {
    @Query(value = "select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);
}
