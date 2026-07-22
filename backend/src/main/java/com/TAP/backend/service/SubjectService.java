package com.TAP.backend.service;

import com.TAP.backend.dto.SubjectRequest;
import com.TAP.backend.dto.SubjectResponse;
import com.TAP.backend.dto.SubjectStatusRequest;
import com.TAP.backend.exception.DuplicateResourceException;
import com.TAP.backend.exception.ResourceNotFoundException;
import com.TAP.backend.model.Department;
import com.TAP.backend.model.Subject;
import com.TAP.backend.repository.DepartmentRepository;
import com.TAP.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    // Confirms the department belongs to the caller's college before touching its subjects.
    private Department verifyDepartmentOwnership(Long departmentId, Long collegeId) {
        return departmentRepository.findByIdAndCollegeId(departmentId, collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    public List<SubjectResponse> getAllSubjects(Long departmentId, Long collegeId) {
        verifyDepartmentOwnership(departmentId, collegeId);
        return subjectRepository.findAllByDepartmentId(departmentId)
                .stream()
                .map(SubjectResponse::fromEntity)
                .toList();
    }

    public SubjectResponse getSubjectById(Long subjectId, Long departmentId, Long collegeId) {
        verifyDepartmentOwnership(departmentId, collegeId);
        Subject subject = subjectRepository.findByIdAndDepartmentId(subjectId, departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return SubjectResponse.fromEntity(subject);
    }

    public SubjectResponse createSubject(Long departmentId, Long collegeId, SubjectRequest request) {
        verifyDepartmentOwnership(departmentId, collegeId);

        if (subjectRepository.existsByNameAndDepartmentId(request.getName(), departmentId)) {
            throw new DuplicateResourceException("Subject with this name already exists in this department");
        }

        Subject subject = new Subject();
        subject.setName(request.getName());
        subject.setDepartmentId(departmentId);
        subject.setStatus(Subject.SubjectStatus.ACTIVE);

        Subject saved = subjectRepository.save(subject);
        return SubjectResponse.fromEntity(saved);
    }

    public SubjectResponse updateSubject(Long subjectId, Long departmentId, Long collegeId, SubjectRequest request) {
        verifyDepartmentOwnership(departmentId, collegeId);

        Subject subject = subjectRepository.findByIdAndDepartmentId(subjectId, departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if (!subject.getName().equalsIgnoreCase(request.getName())
                && subjectRepository.existsByNameAndDepartmentId(request.getName(), departmentId)) {
            throw new DuplicateResourceException("Subject with this name already exists in this department");
        }

        subject.setName(request.getName());
        Subject updated = subjectRepository.save(subject);
        return SubjectResponse.fromEntity(updated);
    }

    public SubjectResponse updateStatus(Long subjectId, Long departmentId, Long collegeId, SubjectStatusRequest request) {
        verifyDepartmentOwnership(departmentId, collegeId);

        Subject subject = subjectRepository.findByIdAndDepartmentId(subjectId, departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Subject.SubjectStatus newStatus = Subject.SubjectStatus.valueOf(request.getStatus().toUpperCase());
        subject.setStatus(newStatus);

        Subject updated = subjectRepository.save(subject);
        return SubjectResponse.fromEntity(updated);
    }
}