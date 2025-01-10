package openerp.openerpresourceserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable OAuth2 authentication for testing
        http.oauth2ResourceServer().jwt();

        // Enable anonymous access
        http.anonymous();

        // State-less session (state in access-token only)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Disable CSRF because of state-less session-management
        http.csrf().disable();

        // Route security - permit all requests
        http
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache()) // Not cache request because of having frontend
                .and()
                .httpBasic()
                .disable()
                .headers()
                .frameOptions()
                .disable();

        return http.build();
    }
}