package com.TAP.backend.controller;

import com.TAP.backend.dto.ApiResponse;
import com.TAP.backend.dto.DepartmentRequest;
import com.TAP.backend.dto.DepartmentResponse;
import com.TAP.backend.dto.DepartmentStatusRequest;
import com.TAP.backend.security.SecurityUtil;
import com.TAP.backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        List<DepartmentResponse> departments = departmentService.getAllDepartments(collegeId);
        return ResponseEntity.ok(ApiResponse.success("Departments fetched successfully", departments));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartment(@PathVariable Long departmentId) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        DepartmentResponse department = departmentService.getDepartmentById(departmentId, collegeId);
        return ResponseEntity.ok(ApiResponse.success("Department fetched successfully", department));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        DepartmentResponse created = departmentService.createDepartment(request, collegeId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Department created successfully", created));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long departmentId,
            @Valid @RequestBody DepartmentRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        DepartmentResponse updated = departmentService.updateDepartment(departmentId, request, collegeId);
        return ResponseEntity.ok(ApiResponse.success("Department updated successfully", updated));
    }

    @PatchMapping("/{departmentId}/status")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateStatus(
            @PathVariable Long departmentId,
            @Valid @RequestBody DepartmentStatusRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        DepartmentResponse updated = departmentService.updateStatus(departmentId, request, collegeId);
        return ResponseEntity.ok(ApiResponse.success("Department status updated successfully", updated));
    }
}
