package com.organization.project.course.services.impl;

import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.model.UserModel;
import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.repository.UserRepository;
import com.organization.project.course.services.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
    UserRepository userRepository;

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
        courseRepository.deleteCourseUserByCourse(courseModel.getCourseId());
        courseRepository.delete(courseModel);
    }
    @Transactional
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
//TODO: deve ser feito em JDBC, foi invertiva a relação de dependencias para ser add curso ao usuário
    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID userId, UUID courseId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        Optional<CourseModel> courseModelOptional = courseRepository.findById(courseId);
        if(userModelOptional.isPresent() && courseModelOptional.isPresent()){
            userModelOptional.get().getCourseList().add(courseModelOptional.get());
            userRepository.save(userModelOptional.get());

            //courseRepository.saveSubscriptionUserInCourse(userId, courseId);
        }else {
            log.error("fail in subscription user in course");
        }


    }

    @Override
    public boolean exitsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.exitsByCourseAndUser(courseId, userId) == BigInteger.ONE;
    }
}
