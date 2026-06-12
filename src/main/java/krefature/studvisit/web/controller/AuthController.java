package krefature.studvisit.web.controller;

import jakarta.validation.Valid;
import krefature.studvisit.security.AuthService;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.auth.AuthLoginRequest;
import krefature.studvisit.web.dto.auth.AuthRefreshRequest;
import krefature.studvisit.web.dto.auth.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public StandardResponse<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return new StandardResponse<>(true, authService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/refresh")
    public StandardResponse<AuthResponse> refresh(@Valid @RequestBody AuthRefreshRequest request) {
        return new StandardResponse<>(true, authService.refresh(request.getRefreshToken()));
    }
}
