package com.aux.spring_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aux.spring_app.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(encoder());
    provider.setUserDetailsService(customUserDetailsService());
    return provider;
  }

  @Bean
  public CustomUserDetailsService customUserDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/css/**").permitAll()
            .requestMatchers("/svg/**").permitAll()
            .requestMatchers("/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/register-save").permitAll()
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login")
            .permitAll())
        .logout((logout) -> logout
            .logoutSuccessUrl("/login")
            .permitAll());

    return http.build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
