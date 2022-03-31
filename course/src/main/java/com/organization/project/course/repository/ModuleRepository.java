package com.organization.project.course.repository;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.ModuleModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {
//exemplo de force EAGER
//    @EntityGraph(attributePaths = {"course"})
//    ModuleModel findByTitle(String title);

//    @Modifying - utilizar para customizações de update, delete insert etc

    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_modules where course_course_id = :courseId and module_id= CourseModel :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId")UUID courseId,@Param("moduleId") UUID moduleId);
}
