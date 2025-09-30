package com.baontin.jobportal.config;

import com.baontin.jobportal.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
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

        /*
        formLogin()
            → Enables form-based login.
            → loginPage("/login") = tells Spring Security to use your custom login page instead of the default.
            → permitAll() = anyone can access the login page, even if not authenticated.
            → successHandler(customAuthenticationSuccessHandler) = delegate login success logic to your custom handler.

        logout()
            → logoutUrl("/logout") = when user hits /logout, Spring clears their session.
            → logoutSuccessUrl("/") = redirect to home page after logging out.

        cors() and csrf()
            → You enabled CORS with defaults and disabled CSRF (common in APIs but careful for production).

        * */

        http.formLogin(form -> form.loginPage("/login").permitAll()
                .successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                }).cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable());

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


