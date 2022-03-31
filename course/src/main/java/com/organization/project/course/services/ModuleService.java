package com.organization.project.course.services;

import com.organization.project.course.model.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    void delete(ModuleModel module);

    ModuleModel save(ModuleModel module);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    Optional<ModuleModel> findById(UUID moduleId);

    List<ModuleModel> findAllByCourse(UUID courseId);

    Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);
}
