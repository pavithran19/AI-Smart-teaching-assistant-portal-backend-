package com.TAP.backend.service;

import com.TAP.backend.dto.DepartmentRequest;
import com.TAP.backend.dto.DepartmentResponse;
import com.TAP.backend.dto.DepartmentStatusRequest;
import com.TAP.backend.exception.ResourceNotFoundException;
import com.TAP.backend.exception.DuplicateResourceException;
import com.TAP.backend.model.Department;
import com.TAP.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentResponse> getAllDepartments(Long collegeId) {
        return departmentRepository.findAllByCollegeId(collegeId)
                .stream()
                .map(DepartmentResponse::fromEntity)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id, Long collegeId) {
        Department department = departmentRepository.findByIdAndCollegeId(id, collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return DepartmentResponse.fromEntity(department);
    }

    public DepartmentResponse createDepartment(DepartmentRequest request, Long collegeId) {
        if (departmentRepository.existsByNameAndCollegeId(request.getName(), collegeId)) {
            throw new DuplicateResourceException("Department with this name already exists");
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setCollegeId(collegeId);
        department.setStatus(Department.DepartmentStatus.ACTIVE);

        Department saved = departmentRepository.save(department);
        return DepartmentResponse.fromEntity(saved);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request, Long collegeId) {
        Department department = departmentRepository.findByIdAndCollegeId(id, collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Only check for duplicates if the name is actually changing.
        if (!department.getName().equalsIgnoreCase(request.getName())
                && departmentRepository.existsByNameAndCollegeId(request.getName(), collegeId)) {
            throw new DuplicateResourceException("Department with this name already exists");
        }

        department.setName(request.getName());
        Department updated = departmentRepository.save(department);
        return DepartmentResponse.fromEntity(updated);
    }

    public DepartmentResponse updateStatus(Long id, DepartmentStatusRequest request, Long collegeId) {
        Department department = departmentRepository.findByIdAndCollegeId(id, collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Department.DepartmentStatus newStatus =
                Department.DepartmentStatus.valueOf(request.getStatus().toUpperCase());
        department.setStatus(newStatus);

        Department updated = departmentRepository.save(department);
        return DepartmentResponse.fromEntity(updated);
    }
}