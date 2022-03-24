package com.organization.project.course.services.impl;

import com.organization.project.course.repository.CourseRepository;
import com.organization.project.course.repository.ModuleRepository;
import com.organization.project.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    ModuleRepository moduleRepository;
}
