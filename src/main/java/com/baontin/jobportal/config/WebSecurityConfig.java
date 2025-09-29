package com.baontin.jobportal.config;

import com.baontin.jobportal.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    private final String[] publicUrl = {"/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers(publicUrl).permitAll();
            auth.anyRequest().authenticated();
        });
        return http.build();
    }

    // tell Spring Security (SC) how to find our uses how to authenticate password
    // defines how to perform authentication.
    /* DaoAuthenticationProvider:
    - A built-in implementation of AuthenticationProvider.
    - ready-made provider for DB authentication (fits your project perfectly).
    * */

    /*
    1. Login request → user enters email + password.
    2. DaoAuthenticationProvider:
        * Calls your UserDetailsService to fetch user from DB.
        * Wraps result in UserDetails.
        * Uses PasswordEncoder to verify password.
    3. If success → user is authenticated, Spring stores the Authentication in the SecurityContext.
    * */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // tell SC how to retrieve the users from the DB (tells it where to load users from.)
        // Using CustomUserDetailsService to fetch the user from DB.
        // (don't need call method loadUserByUsername, DaoAuthenticationProvider auto do it)
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }

    // tell SC how to authenticate pass (plaintext or encryption)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


