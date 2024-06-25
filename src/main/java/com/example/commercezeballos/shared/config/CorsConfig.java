package com.example.commercezeballos.shared.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        //Define the origin, methods, and headers that are allowed
        //Define the origin
        List<String> allowedOrigins = Arrays.asList("http://localhost:4200", "https://test-zeballos.vercel.app/");

        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);

        //Define the headers
        config.addAllowedHeader("*");

        //Define the methods
        config.addAllowedMethod("*");

        //Define the path
        source.registerCorsConfiguration("/**", config);


        return new CorsFilter(source);
    }
}
