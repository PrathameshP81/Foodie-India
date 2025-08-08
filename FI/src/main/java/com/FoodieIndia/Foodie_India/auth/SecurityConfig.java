package com.FoodieIndia.Foodie_India.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // open
                        .requestMatchers("/auth/**").authenticated()

                        // User
                        .requestMatchers("/user/**").permitAll()

                        // Recepie
                        .requestMatchers(HttpMethod.GET, "/recepie/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/recepie/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/recepie/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/recepie/**").hasRole("ADMIN")

                        // Plan
                        .requestMatchers(HttpMethod.GET, "/plan/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/plan").hasRole("ADMIN") // For Create Plan
                        .requestMatchers(HttpMethod.POST, "/plan/*").hasRole("USER") // For Purchase Plan
                        .requestMatchers(HttpMethod.PUT, "/plan/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/plan/**").hasRole("ADMIN")

                        // Subscriptions

                        .requestMatchers(HttpMethod.GET, "/subscription/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/subscription").hasRole("SUPER_ADMIN") // For Create sub.
                        .requestMatchers(HttpMethod.POST, "/subscription/*").hasRole("ADMIN") // For Purchase sub.
                        .requestMatchers(HttpMethod.PUT, "/subscription/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/subscription/**").hasRole("SUPER_ADMIN")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false); // Set true only if sending credentials
        config.setAllowedOriginPatterns(Arrays.asList("*")); // Handles "null" and all
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}