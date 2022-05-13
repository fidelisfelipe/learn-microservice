package com.organization.project.authuser.services;

import com.organization.project.authuser.models.UserModel;
import com.organization.project.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();
    Optional<UserModel> findById(UUID userId);
    void delete(UserModel userModel);
    void save(UserModel userModel);
    UserModel saveUser(UserModel userModel);
    void deleteUser(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    UserModel updatePassword(UserModel userModel);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
}
