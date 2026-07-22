package com.TAP.backend.controller;

import com.TAP.backend.dto.ApiResponse;
import com.TAP.backend.dto.SubjectRequest;
import com.TAP.backend.dto.SubjectResponse;
import com.TAP.backend.dto.SubjectStatusRequest;
import com.TAP.backend.security.SecurityUtil;
import com.TAP.backend.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments/{departmentId}/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getAllSubjects(
            @PathVariable Long departmentId) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        List<SubjectResponse> subjects = subjectService.getAllSubjects(departmentId, collegeId);
        return ResponseEntity.ok(ApiResponse.success("Subjects fetched successfully", subjects));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubject(
            @PathVariable Long departmentId,
            @PathVariable Long subjectId) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        SubjectResponse subject = subjectService.getSubjectById(subjectId, departmentId, collegeId);
        return ResponseEntity.ok(ApiResponse.success("Subject fetched successfully", subject));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(
            @PathVariable Long departmentId,
            @Valid @RequestBody SubjectRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        SubjectResponse created = subjectService.createSubject(departmentId, collegeId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subject created successfully", created));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(
            @PathVariable Long departmentId,
            @PathVariable Long subjectId,
            @Valid @RequestBody SubjectRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        SubjectResponse updated = subjectService.updateSubject(subjectId, departmentId, collegeId, request);
        return ResponseEntity.ok(ApiResponse.success("Subject updated successfully", updated));
    }

    @PatchMapping("/{subjectId}/status")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateStatus(
            @PathVariable Long departmentId,
            @PathVariable Long subjectId,
            @Valid @RequestBody SubjectStatusRequest request) {
        Long collegeId = SecurityUtil.getCurrentUserCollegeId();
        SubjectResponse updated = subjectService.updateStatus(subjectId, departmentId, collegeId, request);
        return ResponseEntity.ok(ApiResponse.success("Subject status updated successfully", updated));
    }
}