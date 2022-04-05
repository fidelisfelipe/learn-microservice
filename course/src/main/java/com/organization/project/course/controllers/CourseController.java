package com.organization.project.course.controllers;

import com.organization.project.course.dtos.CourseDto;
import com.organization.project.course.model.CourseModel;
import com.organization.project.course.services.CourseService;
import com.organization.project.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CourseDto dto){
        var course = new CourseModel();
        BeanUtils.copyProperties(dto, course);
        courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "courseId") UUID courseId){
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        courseService.delete(courseOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> update(@PathVariable(value = "courseId") UUID courseId,
                                         @RequestBody @Valid CourseDto dto){
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        var courseModel = courseOptional.get();

        BeanUtils.copyProperties(dto, courseModel);

        courseModel.setCourseId(courseOptional.get().getCourseId());

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel));
    }
    @GetMapping
    public ResponseEntity<Object> getAll(SpecificationTemplate.CourseSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                         @RequestParam(required = false) UUID userId){
        Page<CourseModel> courseList = null;

        if(userId != null){
            courseList = courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable);
        }else{
            courseList = courseService.findAll(spec, pageable);
        }

        if(courseList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("empty courses");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseList);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "courseId") UUID courseId){
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
    }
}
