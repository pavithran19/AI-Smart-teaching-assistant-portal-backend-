package com.TAP.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DepartmentStatus status = DepartmentStatus.ACTIVE;

    // Every college-scoped entity carries collegeId — this is what
    // makes multi-college isolation work later without a rewrite.
    @Column(name = "college_id", nullable = false)
    private Long collegeId;

    public enum DepartmentStatus {
        ACTIVE, INACTIVE
    }
}