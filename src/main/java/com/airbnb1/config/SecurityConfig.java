package com.airbnb1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfig {//why used that because we open the Url

    private JWTRequestFilter jwtRequestFilter;//this is the object

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();//this will work 3.0.0 version of spring security
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);//when we run the file config file will automatically run//it will run our custom filter method first
        //where is custom filter method is public class JWTRequestFilter extends OncePerRequestFilter
        http.authorizeHttpRequests().
        requestMatchers("/api/v1/users/addUser","/api/v1/users/login").permitAll()//this url every one can access//using java 8 remove request and replace with antMathcer
                .requestMatchers("api/v1/countries/addCountry").hasRole("ADMIN")
                .requestMatchers("api/v1/users/profile").hasAnyRole("ADMIN","USER")
        .anyRequest().authenticated();//it is securing other URL
        return http.build();//here http is object the build method all the information into that object and its return back SecurityFilterChain and then goes to a spring security
    }

}

