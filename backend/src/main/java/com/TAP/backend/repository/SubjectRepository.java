package com.TAP.backend.repository;

import com.TAP.backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByDepartmentId(Long departmentId);

    Optional<Subject> findByIdAndDepartmentId(Long id, Long departmentId);

    boolean existsByNameAndDepartmentId(String name, Long departmentId);
}