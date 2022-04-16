package com.organization.project.course.clients;

import com.organization.project.course.dtos.CourseUserDto;
import com.organization.project.course.dtos.ResponsePageDto;
import com.organization.project.course.dtos.UserDto;
import com.organization.project.course.model.CourseUserModel;
import com.organization.project.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${api.url.auth}")
    String REQUEST_URL_AUTH;

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        List<UserDto> searchResult = null;
        String url = REQUEST_URL_AUTH +utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.info("Request url {}", url);
        try{
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() { };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("elements users size {}", searchResult.size());
        }catch (HttpStatusCodeException e){
            log.error("Error: {}", e);
            throw e;
        }
        log.info("End request / courseId {}", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDto> getOneUserById(UUID userId){
        String url= REQUEST_URL_AUTH + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = REQUEST_URL_AUTH + "/users/" + userId + "/courses/subscription";
        CourseUserDto courseUserDto = new CourseUserDto();
        courseUserDto.setUserId(userId);
        courseUserDto.setCourseId(courseId);
        restTemplate.postForObject(url, courseUserDto, String.class);
    }

    public void deleteCourseInAuthUser(UUID courseId) {
        String url = REQUEST_URL_AUTH + "/users/courses/"+courseId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
