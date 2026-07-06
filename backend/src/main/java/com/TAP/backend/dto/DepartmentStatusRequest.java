package com.TAP.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentStatusRequest {

    @NotNull(message = "Status is required")
    private String status; // "ACTIVE" or "INACTIVE"
}