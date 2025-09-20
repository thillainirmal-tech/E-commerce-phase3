package com.example.minor_project_01.config;

import com.example.minor_project_01.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //default strength 10
    }

    @Bean
    public DaoAuthenticationProvider  daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,DaoAuthenticationProvider provider) throws Exception {
        httpSecurity
                .authenticationProvider(provider)
                .csrf(csrf -> csrf.disable()) // ok for API/dev; enable in prod where appropriate
                .authorizeHttpRequests(auth -> auth
                        // protect both /api/admin/** and /admin/**
                        .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/customer/**").hasRole(Role.CUSTOMER.name())
                        .requestMatchers("/api/seller/**").hasRole(Role.SELLER.name())
                        .requestMatchers("/public/**", "/content/**", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                // if your login form posts "email" and "password", set usernameParameter:
                .formLogin(form -> form
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


        public static void main(String[] args) {
            BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
            System.out.println(enc.encode("seller1212"));
        }

}
