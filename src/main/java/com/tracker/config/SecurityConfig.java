package com.tracker.config;

import com.tracker.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/welcome", "/login", "/signup", "/assets/**").permitAll()
                .requestMatchers("/home", "/moods", "/add-mood", "/back-to-home").authenticated()
                .requestMatchers("/log-mood/**").authenticated()
                .requestMatchers("/set-goal/**").authenticated()
                .requestMatchers("/gratitude/**").authenticated()
                .requestMatchers("/self-appreciation/**").authenticated()
                .requestMatchers("/weekly-mood-analysis/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)  // Create session if required
                .invalidSessionUrl("/login")  // Redirect to login page if session is invalid
                //.maximumSessions(1)  // Optional: Limit the number of concurrent sessions per user
                //.maxSessionsPreventsLogin(true) // Optional: Prevent login if the session limit is exceeded
            );

        return http.build();
    }
}
