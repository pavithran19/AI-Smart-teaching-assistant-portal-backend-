package com.TAP.backend.service;

import com.TAP.backend.model.College;
import com.TAP.backend.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    // Get all colleges (for superadmin dashboard)
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    // Get only pending requests
    public List<College> getPendingColleges() {
        return collegeRepository.findByStatus(College.Status.PENDING);
    }

    // Get only active colleges
    public List<College> getActiveColleges() {
        return collegeRepository.findByStatus(College.Status.ACTIVE);
    }

    // College submits a join request
    public College requestAccess(College college) {
        if (collegeRepository.existsByAdminEmail(college.getAdminEmail())) {
            throw new RuntimeException("A request from this email already exists");
        }
        return collegeRepository.save(college);
    }

    // Superadmin accepts request
    public College acceptCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
        college.setStatus(College.Status.ACTIVE);
        return collegeRepository.save(college);
    }

    // Superadmin rejects request
    public College rejectCollege(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
        college.setStatus(College.Status.REJECTED);
        return collegeRepository.save(college);
    }

    // Get single college by id
    public College getCollegeById(Long id) {
        return collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
    }

    public College save(College college) {
        return collegeRepository.save(college);
    }

    public College updateCollege(Long id, College updated) {
        College existing = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
        existing.setName(updated.getName());
        existing.setLocation(updated.getLocation());
        existing.setAddress(updated.getAddress());
        existing.setPhone(updated.getPhone());
        existing.setCollegeCode(updated.getCollegeCode());
        existing.setCollegeEmail(updated.getCollegeEmail());
        return collegeRepository.save(existing);
    }

    public void deleteCollege(Long id) {
        collegeRepository.deleteById(id);
    }

    public void inviteCollege(Map<String, String> body) {
        College college = new College();
        college.setName(body.get("collegeName"));
        college.setCollegeCode(body.get("collegeCode"));
        college.setCollegeEmail(body.get("collegeEmail"));
        college.setAdminName(body.get("adminName"));
        college.setAdminEmail(body.get("adminEmail"));
        college.setPhone(body.get("phone"));
        college.setAddress(body.get("address"));
        college.setStatus(College.Status.PENDING);
        collegeRepository.save(college);
    }
}