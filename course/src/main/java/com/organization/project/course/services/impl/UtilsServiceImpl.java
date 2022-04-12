package com.organization.project.course.services.impl;

import com.organization.project.course.services.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UtilsServiceImpl implements UtilsService {

    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable){
        return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size=" +
                pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }

}
