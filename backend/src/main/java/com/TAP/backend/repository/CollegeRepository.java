package com.TAP.backend.repository;

import com.TAP.backend.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {
    List<College> findByStatus(College.Status status);
    boolean existsByAdminEmail(String adminEmail);
}