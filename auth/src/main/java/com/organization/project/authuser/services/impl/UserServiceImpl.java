package com.organization.project.authuser.services.impl;

import com.organization.project.authuser.clients.CourseClient;
import com.organization.project.authuser.models.UserCourseModel;
import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.repositories.UserCourseRepository;
import com.organization.project.authuser.repositories.UserRepository;
import com.organization.project.authuser.services.UserService;
import com.organization.project.authuser.specifications.SpecificationTemplate;
import org.apache.catalina.User;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseClient courseClient;

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
        boolean deleteUserCourseInCourse =  Boolean.FALSE;;
        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
        if(!userCourseModelList.isEmpty()){
            userCourseRepository.deleteAll(userCourseModelList);
            deleteUserCourseInCourse = Boolean.TRUE;
        }
        userRepository.delete(userModel);
        if(deleteUserCourseInCourse){
            courseClient.deleteUserInCourse(userModel.getUserId());
        }
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
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
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }


}
