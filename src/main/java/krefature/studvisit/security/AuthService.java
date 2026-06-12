package krefature.studvisit.security;

import krefature.studvisit.web.dto.auth.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AppUserDetailsService userDetailsService;

    public AuthResponse login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
            return buildResponse(principal);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Неверный логин или пароль.");
        }
    }

    public AuthResponse refresh(String refreshToken) {
        if (jwtTokenService.extractTokenType(refreshToken, JwtTokenType.REFRESH) != JwtTokenType.REFRESH) {
            throw new BadCredentialsException("Передан некорректный refresh token.");
        }
        String username = jwtTokenService.extractUsername(refreshToken, JwtTokenType.REFRESH);
        AppUserPrincipal principal = userDetailsService.loadUserByUsername(username);
        if (!jwtTokenService.isTokenValid(refreshToken, principal, JwtTokenType.REFRESH)) {
            throw new BadCredentialsException("Refresh token недействителен.");
        }
        return buildResponse(principal);
    }

    private AuthResponse buildResponse(AppUserPrincipal principal) {
        long now = System.currentTimeMillis();
        return new AuthResponse(
                jwtTokenService.generateAccessToken(principal),
                jwtTokenService.generateRefreshToken(principal),
                "Bearer",
                now + jwtTokenService.getAccessExpirationMs(),
                now + jwtTokenService.getRefreshExpirationMs(),
                principal.getRole(),
                principal.getUsername()
        );
    }
}
