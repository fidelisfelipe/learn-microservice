package com.organization.project.course.services.impl;

import com.organization.project.course.repository.CourseUserRepository;
import com.organization.project.course.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;
}
