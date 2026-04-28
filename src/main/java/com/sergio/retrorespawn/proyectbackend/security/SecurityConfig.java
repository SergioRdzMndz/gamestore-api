package com.sergio.retrorespawn.proyectbackend.security;

import com.sergio.retrorespawn.proyectbackend.filter.JwtAuthenticationFilter;
import com.sergio.retrorespawn.proyectbackend.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter, UserRepository userRepository) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable()) // <- agrega esto
                .formLogin(formLogin -> formLogin.disable()) // <- agrega esto
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/games/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/carts/**").authenticated()
                        .requestMatchers("/api/cart-items/**").authenticated()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider(userRepository))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
