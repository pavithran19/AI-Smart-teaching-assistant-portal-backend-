package com.TAP.backend.controller;

import com.TAP.backend.dto.ApiResponse;
import com.TAP.backend.model.User;
import com.TAP.backend.repository.UserRepository;
import com.TAP.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/**")
public class AuthController {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userrepository.findByEmail(email);

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Invalid email or password"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(email, user.getRole().name(), user.getId(), user.getCollegeId());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("role", user.getRole().name());
        data.put("userId", user.getId());
        data.put("collegeId", user.getCollegeId());
        data.put("name", user.getName());

        return ResponseEntity.ok(ApiResponse.success("Login successful", data));
    }
}
