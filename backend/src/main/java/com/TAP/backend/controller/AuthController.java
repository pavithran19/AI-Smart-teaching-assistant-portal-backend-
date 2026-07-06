package com.TAP.backend.controller;

import com.TAP.backend.dto.ApiResponse;
import com.TAP.backend.model.User;
import com.TAP.backend.repository.userrepository;
import com.TAP.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/**")
public class AuthController {

    @Autowired
    private userrepository userrepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        System.out.println("Email received: " + email);
        System.out.println("Password received: " + password);

        Optional<User> userOpt = userrepository.findByEmail(email);
        System.out.println("User found: " + userOpt.isPresent());

        if (userOpt.isPresent()) {
            System.out.println("DB password: " + userOpt.get().getPassword());
            System.out.println("Entered password: " + password);
        }

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Invalid email or password"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(email, user.getRole().name(), user.getId(), user.getCollegeId());

        Map<String, Object> data = Map.of(
                "token", token,
                "role", user.getRole().name(),
                "userId", user.getId(),
                "collegeId", user.getCollegeId(),
                "name", user.getName()
        );

        return ResponseEntity.ok(ApiResponse.success("Login successful", data));
    }
}
