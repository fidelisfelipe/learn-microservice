package com.organization.project.course.controllers;

import com.organization.project.course.dtos.CourseDto;
import com.organization.project.course.dtos.ModuleDto;
import com.organization.project.course.model.CourseModel;
import com.organization.project.course.model.ModuleModel;
import com.organization.project.course.services.CourseService;
import com.organization.project.course.services.ModuleService;
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
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Object> save(@PathVariable(value="courseId") UUID courseId,
            @RequestBody @Valid ModuleDto dto){
        Optional<CourseModel> courseOptional = courseService.findById(courseId);

        if(!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        var module = new ModuleModel();
        BeanUtils.copyProperties(dto, module);

        module.setCourse(courseOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "courseId") UUID courseId,
                                         @PathVariable(value = "moduleId") UUID moduleId){
        Optional<ModuleModel> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        moduleService.delete(moduleOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
    }

    @PutMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> update(@PathVariable(value = "courseId") UUID courseId,
                                         @PathVariable(value = "moduleId") UUID moduleId,
                                         @RequestBody @Valid Module dto){
        Optional<ModuleModel> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        var module = new ModuleModel();
        BeanUtils.copyProperties(dto, module);

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAll(@PathVariable(value = "courseId") UUID courseId,
                                         SpecificationTemplate.ModuleSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ModuleModel> moduleList = moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(moduleList);
    }

    @GetMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "courseId") UUID courseId,
                                         @PathVariable(value = "moduleId") UUID moduleId){
        Optional<ModuleModel> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleOptional.get());
    }

}
