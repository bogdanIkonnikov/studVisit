package krefature.studvisit.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRefreshRequest {
    @NotBlank(message = "Refresh token должен быть передан")
    private String refreshToken;
}
