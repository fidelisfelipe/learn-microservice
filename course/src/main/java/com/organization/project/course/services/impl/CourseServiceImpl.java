package com.organization.project.course.services.impl;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonsRepository lessonsRepository;

    @Transactional
    @Override
    public void delete(CourseModel course) {
        List<ModuleModel> moduleList = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
        if(!moduleList.isEmpty()){
            for (ModuleModel item : moduleList){
                List<LessonModel> lessonList = lessonsRepository.findAllLessonsIntoModule(item.getModuleId());
                if(!lessonList.isEmpty()){
                    lessonsRepository.deleteAll(lessonList);
                }
            }
            moduleRepository.deleteAll(moduleList);
        }
        courseRepository.delete(course);

    }

    @Override
    public CourseModel save(CourseModel course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
    }
}
