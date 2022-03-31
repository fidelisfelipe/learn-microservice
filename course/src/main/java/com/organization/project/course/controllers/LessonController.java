package com.organization.project.course.controllers;

import com.organization.project.course.dtos.CourseDto;
import com.organization.project.course.dtos.LessonDto;
import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.LessonModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.services.LessonService;
import com.organization.project.course.services.ModuleService;
import com.organization.project.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;


    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> save(
            @PathVariable(value="moduleId") UUID moduleId,
            @RequestBody @Valid LessonDto dto){
        Optional<ModuleModel> moduleOptional = moduleService.findById(moduleId);

        if(!moduleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found");
        }

        var lesson = new LessonModel();
        BeanUtils.copyProperties(dto, lesson);
        lesson.setModule(moduleOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
    }

    @DeleteMapping("/{moduleId}/modules/{lessonId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "moduleId") UUID moduleId,
                                         @PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        lessonService.delete(lessonOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Object> update(@PathVariable(value = "lessonId") UUID lessonId,
                                         @RequestBody @Valid LessonDto dto){
        Optional<LessonModel> lessonOptional = lessonService.findById(lessonId);
        if(!lessonOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        var lessonModel = lessonOptional.get();

        BeanUtils.copyProperties(dto, lessonModel);

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonModel> lessonOptional = lessonService.findById(lessonId);
        if(!lessonOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonOptional.get());
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<LessonModel> lessonList = lessonService.findAll();
        if(lessonList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("empty courses");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonList);
    }
}
