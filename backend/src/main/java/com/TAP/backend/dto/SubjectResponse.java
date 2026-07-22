package com.TAP.backend.dto;

import com.TAP.backend.model.Subject;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class SubjectResponse {

    private Long id;
    private String name;
    private String status;
    private Long departmentId;

    public static SubjectResponse fromEntity(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getStatus().name(),
                subject.getDepartmentId()
        );
    }
}