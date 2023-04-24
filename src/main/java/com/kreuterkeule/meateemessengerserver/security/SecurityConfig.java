package com.kreuterkeule.meateemessengerserver.security;

import com.kreuterkeule.meateemessengerserver.service.CustomUserDetailsService;
import com.kreuterkeule.meateemessengerserver.service.UniqueTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UniqueTokenProvider tokenProvider() {
        return new UniqueTokenProvider();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .httpBasic()
                .and()
                .authorizeHttpRequests((request) -> {
                    try {
                        request
                        .requestMatchers("/api/login", "/error", "/api/register").permitAll()
                        .anyRequest().authenticated()
                                .and()
                                .cors().configurationSource(request1 -> new CorsConfiguration().applyPermitDefaultValues());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(bCryptPasswordEncoder);
        return delegatingPasswordEncoder;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}
