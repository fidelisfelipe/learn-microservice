package com.organization.project.course.services.impl;

import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.LessonsRepository;
import com.organization.project.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    LessonsRepository lessonsRepository;
}
