package com.organization.project.authuser.services.impl;

import com.organization.project.authuser.clients.CourseClient;
import com.organization.project.authuser.models.enums.ActionType;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.publishers.UserEventPublisher;
import com.organization.project.authuser.repositories.UserRepository;
import com.organization.project.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {
        save(userModel);
        userEventPublisher.publishEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
        return userModel;
    }

    @Transactional
    @Override
    public void deleteUser(UserModel userModel) {
        delete(userModel);
        userEventPublisher.publishEvent(userModel.convertToUserEventDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserModel userModel) {
        save(userModel);
        userEventPublisher.publishEvent(userModel.convertToUserEventDto(), ActionType.UPDATE);
        return userModel;
    }

    @Transactional
    @Override
    public UserModel updatePassword(UserModel userModel) {
        save(userModel);
        return userModel;
    }


    @Override
    public boolean existsByUsername(String username) { return userRepository.existsByUsername(username); }

    @Override
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }

    @Override
    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec,
                                   Pageable pageable) { return userRepository.findAll(spec, pageable);
    }


}
