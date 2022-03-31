package com.organization.project.course.services;

import com.organization.project.course.model.LessonModel;
import com.organization.project.course.specifications.SpecificationTemplate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(LessonModel lesson);

    Optional<LessonModel> findById(UUID lessonId);

    List<LessonModel> findAll();

    void delete(LessonModel lessonModel);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);
}
