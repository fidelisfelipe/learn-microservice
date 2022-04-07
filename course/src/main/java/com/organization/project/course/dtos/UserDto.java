package com.organization.project.course.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.organization.project.course.enums.UserStatus;
import com.organization.project.course.enums.UserType;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageURL;

    private UserStatus userStatus;
    private UserType userType;
}
