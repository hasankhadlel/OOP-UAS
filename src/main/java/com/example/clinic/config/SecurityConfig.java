package com.example.clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/patients/**",
                                "/doctors/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/medical-records/add/**",
                                "/medical-records/save"
                        )
                        .hasRole("DOCTOR")

                        .requestMatchers(
                                "/medical-records/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "DOCTOR",
                                "PATIENT"
                        )

                        .requestMatchers(
                                "/appointments/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "DOCTOR",
                                "PATIENT"
                        )

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl(
                                "/",
                                true
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

}