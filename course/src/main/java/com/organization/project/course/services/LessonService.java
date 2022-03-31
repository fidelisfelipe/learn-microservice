package com.organization.project.course.services;

import com.organization.project.course.model.LessonModel;
import com.organization.project.course.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(LessonModel lesson);

    Optional<LessonModel> findById(UUID lessonId);

    List<LessonModel> findAll();

    void delete(LessonModel lessonModel);

    Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);
}
