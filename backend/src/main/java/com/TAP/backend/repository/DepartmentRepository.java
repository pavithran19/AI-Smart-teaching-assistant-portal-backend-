package com.TAP.backend.repository;

import com.TAP.backend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Tenant-scoped queries — always filter by collegeId, never findAll()
    List<Department> findAllByCollegeId(Long collegeId);

    Optional<Department> findByIdAndCollegeId(Long id, Long collegeId);

    boolean existsByNameAndCollegeId(String name, Long collegeId);
}