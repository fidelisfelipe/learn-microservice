package com.organization.project.course.services;

import com.organization.project.course.model.ModuleModel;

import javax.transaction.Transactional;

public interface ModuleService {
    void delete(ModuleModel module);
}
