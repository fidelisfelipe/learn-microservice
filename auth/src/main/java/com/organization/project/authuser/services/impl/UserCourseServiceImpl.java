package com.organization.project.authuser.services.impl;

import com.organization.project.authuser.repositories.UserCourseRepository;
import com.organization.project.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    UserCourseRepository userCourseRepository;
}
