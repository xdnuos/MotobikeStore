package com.example.motobikestore.configuration;

import com.example.motobikestore.security.JwtAuthenticationFilter;
import com.example.motobikestore.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
//    private final JwtFilter jwtFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers("/api/v1/auth/**",
                            "/api/v1/auth/login",
                            "/api/v1/registration/**",
                            "/api/v1/perfumes/**",
                            "/api/v1/users/cart",
                            "/api/v1/order/**",
                            "/api/v1/review/**",
                            "/websocket", "/websocket/**",
                            "/api/v1/products/get/**",
                            "/img/**",
                            "/static/**",
                            "/auth/**",
                            "/oauth2/**",
                            "/**/*swagger*/**",
                            "/v2/api-docs").permitAll()
                    .anyRequest().authenticated();
                });
        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception
//    { http
//            .csrf(csrf-> csrf.disable())
////            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////            .and()
//            .authorizeHttpRequests((authorizeHttpRequests) -> {
//                authorizeHttpRequests
//                        .requestMatchers("/user/**").permitAll()
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN");
//            });
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return  http.build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}

