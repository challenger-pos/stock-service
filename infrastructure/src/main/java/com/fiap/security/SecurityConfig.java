package com.fiap.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/stock/parts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/stock/parts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/stock/parts/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/stock/reserve").authenticated()
                        .requestMatchers(HttpMethod.POST, "/stock/approveReserve").authenticated()
                        .requestMatchers(HttpMethod.POST, "/stock/cancelReserve").authenticated()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(noopAuthProvider())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider noopAuthProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) {
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}
