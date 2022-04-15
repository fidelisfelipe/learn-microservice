package com.organization.project.course.services.impl;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.CourseUserModel;
import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.CourseUserRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.services.CourseService;
import com.organization.project.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonsRepository lessonsRepository;

    @Autowired
    CourseUserRepository courseUserRepository;

    //TODO: verificar inconsistencia deste metodo
    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if(!moduleModelList.isEmpty()){
            for (ModuleModel module : moduleModelList){
                List<LessonModel> lessonModelList = lessonsRepository.findAllLessonsIntoModule(module.getModuleId());
                if(!lessonModelList.isEmpty()){
                    lessonsRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }

        //TODO: verificar inconsistencia deste ponto
        List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
        if(!courseUserModelList.isEmpty()){
            courseUserRepository.deleteAll(courseUserModelList);
        }
        courseRepository.delete(courseModel);

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
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }
}
