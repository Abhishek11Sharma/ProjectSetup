package com.reuse.predefined.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import com.reuse.predefined.services.UserDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String[] PUBLIC_URL = {
            "/api/user", "/api/login", "/api/shopify/authorize", "/api/shopify/oauth/callback", "/api/shopify/checkout"

    };

    /*
     * private static final String[] SWAGGER_URL = {
     * "/v3/api-docs",
     * "/configuration/ui",
     * "/swagger-resources/**",
     * "/configuration/security",
     * "/swagger-ui/**",
     * "/webjars/**"
     * };
     */

    @Order(2)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URL).permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").authenticated())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/video/**").authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain securityFilterChainn(HttpSecurity http) throws Exception {
        http
                .securityMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/api/**")))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/test").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}