package krefature.studvisit.security;

import krefature.studvisit.infrastructure.entity.AppUser;
import krefature.studvisit.infrastructure.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private AppUserRepository repository;

    @Override
    public AppUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
        return new AppUserPrincipal(user);
    }
}
