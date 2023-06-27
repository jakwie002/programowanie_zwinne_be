package com.project.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.model.Student;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    @CrossOrigin("*")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Student student) {
        authService.register(student);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
