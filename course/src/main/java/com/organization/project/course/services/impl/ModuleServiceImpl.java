package com.organization.project.course.services.impl;

import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonsRepository lessonsRepository;


    @Transactional
    @Override
    public void delete(ModuleModel module) {
        List<LessonModel> lessonList = lessonsRepository.findAllLessonsIntoModule(module.getModuleId());
        if(lessonList.isEmpty()){
            lessonsRepository.deleteAll(lessonList);
        }
        moduleRepository.delete(module);
    }

    @Override
    public ModuleModel save(ModuleModel module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }
}
