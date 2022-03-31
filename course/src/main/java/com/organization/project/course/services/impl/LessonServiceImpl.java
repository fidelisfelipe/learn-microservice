package com.organization.project.course.services.impl;

import com.organization.project.course.model.LessonModel;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.services.LessonService;
import com.organization.project.course.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    LessonsRepository lessonsRepository;

    @Override
    public LessonModel save(LessonModel lesson) {
        return lessonsRepository.save(lesson);
    }

    @Override
    public Optional<LessonModel> findById(UUID lessonId) {
        return lessonsRepository.findById(lessonId);
    }

    @Override
    public List<LessonModel> findAll() {
        return lessonsRepository.findAll();
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonsRepository.delete(lessonModel);
    }

    @Override
    public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
        return lessonsRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        System.out.println("not implemented findLessonIntoModule");
        return Optional.empty();//TODO: not implemented
    }
}
