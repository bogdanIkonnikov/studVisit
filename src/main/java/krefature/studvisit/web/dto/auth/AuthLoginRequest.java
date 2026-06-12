package krefature.studvisit.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLoginRequest {
    @NotBlank(message = "Логин должен быть заполнен")
    private String username;

    @NotBlank(message = "Пароль должен быть заполнен")
    private String password;
}
