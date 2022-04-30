package com.organization.project.course.services.impl;

import com.organization.project.course.clients.AuthUserClient;
import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.UserModel;
import com.organization.project.course.repository.UserRepository;
import com.organization.project.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
}
