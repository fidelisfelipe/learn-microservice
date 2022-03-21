package com.organization.project.authuser.services.impl;

import com.organization.project.authuser.repositories.UserRepository;
import com.organization.project.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;
}
