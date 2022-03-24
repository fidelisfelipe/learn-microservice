package com.organization.project.course.services.impl;

import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;
}
