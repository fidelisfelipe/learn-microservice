package com.organization.project.course.services.impl;

import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
