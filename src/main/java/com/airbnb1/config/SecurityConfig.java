package com.airbnb1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {//why used that because we open the Url
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();//this will work 3.0.0 version of spring security
        http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();//here http is object the build method all the information into that object and its return back SecurityFilterChain and then goes to a spring security
    }

}
