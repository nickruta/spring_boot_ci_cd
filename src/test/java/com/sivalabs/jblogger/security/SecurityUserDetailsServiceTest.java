package com.nickruta.jblogger.security;

import com.nickruta.jblogger.entities.User;
import com.nickruta.jblogger.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    SecurityUserDetailsService securityUserDetailsService;

    @Test
    void loadUserByUsername() throws Exception {
        String email = "admin@gmail.com";
        final User domainUser = new User();
        domainUser.setEmail(email);
        domainUser.setPassword("pwd");
        Optional<User> user = Optional.of(domainUser);

        when(userRepository.findByEmail(email)).thenReturn(user);

        final UserDetails userDetails = securityUserDetailsService.loadUserByUsername(email);
        assertThat(userDetails).isNotNull();
    }

    @Test
    void loadUserByUsernameThrowsUserNameNotFound() throws Exception {
        String email = "admin@gmail.com";

        when(userRepository.findByEmail(email)).thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> {
            securityUserDetailsService.loadUserByUsername(email);
        });

    }
}
