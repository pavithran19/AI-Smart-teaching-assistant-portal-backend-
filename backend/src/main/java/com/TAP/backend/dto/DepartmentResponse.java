package com.TAP.backend.dto;

import com.TAP.backend.model.Department;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentResponse {

    private Long id;
    private String name;
    private String status;

    public static DepartmentResponse fromEntity(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getStatus().name()
        );
    }
}