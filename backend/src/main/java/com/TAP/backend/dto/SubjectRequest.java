package com.TAP.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
    @NotBlank(message = "Subject name is required")
    private String name;
}
