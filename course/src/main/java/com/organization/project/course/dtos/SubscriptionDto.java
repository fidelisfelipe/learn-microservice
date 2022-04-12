package com.organization.project.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
public class SubscriptionDto {
    @NotNull
    private UUID userId;
}
