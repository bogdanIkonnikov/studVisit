package krefature.studvisit.web.dto.auth;

import krefature.studvisit.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long accessExpiresAt;
    private long refreshExpiresAt;
    private UserRole role;
    private String username;
}
