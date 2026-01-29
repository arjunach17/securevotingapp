package com.example.securevoting.controller;

import com.example.securevoting.dto.AuthRequest;
import com.example.securevoting.dto.AuthResponse;
import com.example.securevoting.dto.UserRegistrationRequest;
import com.example.securevoting.entity.User;
import com.example.securevoting.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // Public registration for normal users
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request, false);
        return ResponseEntity.ok(new AuthResponse("User registered with id: " + user.getId()));
    }

    // Admin registration endpoint can be restricted behind ADMIN or create manually
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(
            @Valid @RequestBody UserRegistrationRequest request) {
        User admin = userService.registerUser(request, true);
        return ResponseEntity.ok(new AuthResponse("Admin registered with id: " + admin.getId()));
    }

    // Session-based login; Spring Security will create a session
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        // If authentication succeeds, security context/session is set automatically
        return ResponseEntity.ok(new AuthResponse("Login successful"));
    }

    // Basic logout (relies on Spring Security default /logout endpoint if enabled)
}
