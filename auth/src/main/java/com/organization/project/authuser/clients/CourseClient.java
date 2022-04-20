package com.organization.project.authuser.clients;

import com.organization.project.authuser.dtos.CourseDto;
import com.organization.project.authuser.dtos.ResponsePageDto;
import com.organization.project.authuser.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${api.url.course}")
    String REQUEST_URI_COURSE;

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable){
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;
        List<CourseDto> searchResult = null;
        String url = REQUEST_URI_COURSE +utilsService.createUrl(userId, pageable);
        log.info("Request url {}", url);
        try{
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() { };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("elements courses size {}", searchResult.size());
        }catch (HttpStatusCodeException e){
            log.error("Error: {}", e);
            throw e;
        }
        log.info("End request / userId {}", userId);
        return result.getBody();
    }

    public void deleteUserInCourse(UUID userId) {
        String url = REQUEST_URI_COURSE + "/courses/users/"+userId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
