package com.organization.project.course.clients;

import com.organization.project.course.dtos.ResponsePageDto;
import com.organization.project.course.dtos.UserDto;
import com.organization.project.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        List<UserDto> searchResult = null;
        String url = utilsService.createUrl(courseId, pageable);
        log.info("Request url {}", url);
        try{
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() { };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("elements users size {}", searchResult.size());
        }catch (HttpStatusCodeException e){
            log.error("Error: {}", e);
        }
        log.info("End request / courseId {}", courseId);
        return result.getBody();
    }

}
