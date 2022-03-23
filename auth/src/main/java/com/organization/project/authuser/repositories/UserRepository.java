package com.organization.project.authuser.repositories;

import com.organization.project.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
