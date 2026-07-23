package com.TAP.backend.controller;

import com.TAP.backend.dto.ApiResponse;
import com.TAP.backend.model.College;
import com.TAP.backend.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    @Autowired
    private CollegeService collegeService;

    // Dashboard summary
    @GetMapping("/api/superadmin/dashboard")
    public ResponseEntity<?> getDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalColleges", collegeService.getAllColleges().size());
        data.put("totalDepartments", 0);
        data.put("totalProfessors", 0);
        data.put("totalStudents", 0);
        data.put("totalSubjects", 0);
        return ResponseEntity.ok(ApiResponse.success("Request Successful", data));
    }

    // Get all colleges
    @GetMapping("/api/colleges")
    public ResponseEntity<?> getAllColleges() {
        return ResponseEntity.ok(
                ApiResponse.success("Request Successful", collegeService.getAllColleges())
        );
    }

    // Get college by ID
    @GetMapping("/api/colleges/{collegeId}")
    public ResponseEntity<?> getCollege(@PathVariable Long collegeId) {
        return ResponseEntity.ok(
                ApiResponse.success("Request Successful", collegeService.getCollegeById(collegeId))
        );
    }

    // Add college directly
    @PostMapping("/api/colleges")
    public ResponseEntity<?> addCollege(@RequestBody College college) {
        college.setStatus(College.Status.ACTIVE);
        return ResponseEntity.status(201).body(
                ApiResponse.success("College added successfully", collegeService.save(college))
        );
    }

    // Update college
    @PutMapping("/api/colleges/{collegeId}")
    public ResponseEntity<?> updateCollege(@PathVariable Long collegeId,
                                           @RequestBody College updated) {
        return ResponseEntity.ok(
                ApiResponse.success("College updated", collegeService.updateCollege(collegeId, updated))
        );
    }

    // Delete college
    @DeleteMapping("/api/colleges/{collegeId}")
    public ResponseEntity<?> deleteCollege(@PathVariable Long collegeId) {
        collegeService.deleteCollege(collegeId);
        return ResponseEntity.ok(ApiResponse.success("College deleted", null));
    }

    // Invite college admin (sends email)
    @PostMapping("/api/colleges/invite")
    public ResponseEntity<?> inviteCollege(@RequestBody Map<String, String> body) {
        collegeService.inviteCollege(body);
        return ResponseEntity.ok(
                ApiResponse.success("Invitation sent successfully.", null)
        );
    }

    // Analytics
    @GetMapping("/api/superadmin/analytics")
    public ResponseEntity<?> getAnalytics() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUploads", 0);
        data.put("activeUsers", 0);
        data.put("totalAIRequests", 0);
        data.put("totalAssessments", 0);
        data.put("todayLogins", 0);
        return ResponseEntity.ok(ApiResponse.success("Request Successful", data));
    }

    // AI Monitoring
    @GetMapping("/api/superadmin/ai-monitoring")
    public ResponseEntity<?> getAiMonitoring() {
        Map<String, Object> data = new HashMap<>();
        data.put("todayRequests", 0);
        data.put("successfulRequests", 0);
        data.put("failedRequests", 0);
        data.put("averageResponseTime", "0 sec");
        data.put("aiStatus", "ACTIVE");
        return ResponseEntity.ok(ApiResponse.success("Request Successful", data));
    }

    // Notifications
    @GetMapping("/api/notifications")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok(
                ApiResponse.success("Request Successful", List.of())
        );
    }
}