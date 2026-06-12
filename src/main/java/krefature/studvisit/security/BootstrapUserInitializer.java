package krefature.studvisit.security;

import krefature.studvisit.common.enums.UserRole;
import krefature.studvisit.infrastructure.entity.AppUser;
import krefature.studvisit.infrastructure.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BootstrapUserInitializer {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username:admin}")
    private String adminUsername;
    @Value("${app.bootstrap.admin.password:}")
    private String adminPassword;

    @Bean
    public ApplicationRunner seedAdminUser() {
        return args -> {
            createAdminIfNeeded();
        };
    }

    private void createAdminIfNeeded() {
        if (adminUsername == null || adminUsername.isBlank()) {
            return;
        }
        if (appUserRepository.existsByUsername(adminUsername)) {
            return;
        }
        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException("Для стартового администратора не задан пароль в переменных среды.");
        }
        AppUser user = new AppUser();
        user.setUsername(adminUsername);
        user.setPasswordHash(passwordEncoder.encode(adminPassword));
        user.setRole(UserRole.ADMIN);
        user.setEnabled(true);
        appUserRepository.save(user);
    }
}
