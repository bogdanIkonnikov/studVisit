package krefature.studvisit.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserActionLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(UserActionLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startedAt = System.currentTimeMillis();
        filterChain.doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal principal = authentication != null && authentication.getPrincipal() instanceof AppUserPrincipal userPrincipal
                ? userPrincipal
                : null;

        String username = principal != null ? principal.getUsername() : "anonymous";
        String role = principal != null ? principal.getRole().name() : "ANONYMOUS";
        long durationMs = System.currentTimeMillis() - startedAt;

        log.info(
                "User action: user={}, role={}, method={}, uri={}, status={}, durationMs={}",
                username,
                role,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                durationMs
        );
    }
}
