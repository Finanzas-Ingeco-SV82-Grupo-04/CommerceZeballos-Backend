package com.example.commercezeballos.security_management.infraestructure.config;

import com.example.commercezeballos.security_management.application.services.CustomUserDetailsService;
import com.example.commercezeballos.security_management.domain.jwt.filter.JwtAuthenticationFilter;
import com.example.commercezeballos.security_management.domain.jwt.filter.JwtAuthorizationFilter;
import com.example.commercezeballos.security_management.domain.jwt.utils.JwtUtils;
import com.example.commercezeballos.security_management.infraestructure.repositories.UserRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity//habilita la seguridad a nivel de aplicación
@EnableMethodSecurity//habilita la seguridad a nivel de método
public class SecurityConfig  {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final UserRepository userRepository;

    private final ModelMapperConfig modelMapperConfig;

    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils, JwtAuthorizationFilter jwtAuthorizationFilter, UserRepository userRepository, ModelMapperConfig modelMapperConfig) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userRepository = userRepository;
        this.modelMapperConfig = modelMapperConfig;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager ) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils, userRepository, modelMapperConfig) ;
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/user/sign-in");



        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);



        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth->{
            auth.requestMatchers(
                    "/api/v1/auth/admin/sign-up",
                    "/api/v1/auth/client/sign-up",
                    "/api/v1/auth/user/sign-in",
                    "/api/v1/current-account/**",
                    "api/v1/products/**",
                    "/api/v1/transactions/**",
                    "/api/v1/payments/**",
                    "/api/v1/payment-plans/**",
                    "/api/v1/clients/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"


            ).permitAll();//todas las que no necesitan autenticació



            auth.anyRequest()
                    .authenticated();//todas las demás necesitan autenticación
        });

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }





}