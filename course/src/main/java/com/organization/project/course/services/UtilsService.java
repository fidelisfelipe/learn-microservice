package com.organization.project.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable);
}
