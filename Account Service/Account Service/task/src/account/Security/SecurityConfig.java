package account.Security;

import account.Constants.RoleType;
import account.ExceptionHandler.AccessDeniedHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.requestMatchers("/api/auth/signup/**")
                            .permitAll();
                    auth.requestMatchers("/api/auth/changepass/**")
                            .authenticated();
                    auth.requestMatchers("/api/acct/**")
                            .hasRole(RoleType.ACCOUNTANT.toString());
                    auth.requestMatchers("/api/empl/**")
                            .hasAnyRole(RoleType.ACCOUNTANT.toString(), RoleType.USER.toString());
                    auth.requestMatchers("/api/admin/**")
                            .hasRole(RoleType.ADMINISTRATOR.toString());
                })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}