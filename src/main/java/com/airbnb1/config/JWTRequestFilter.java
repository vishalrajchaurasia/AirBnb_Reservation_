package com.airbnb1.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //handover this class to spring boot its bean lifecycle management
public class JWTRequestFilter extends OncePerRequestFilter {//this abstract class is incomplete method to doFilterInternal method
    //OncePerRequestFilter request is abstract class.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //all the incoming request access with automatically
        String tokenHeader = request.getHeader("Authorization");//is not just a token i call that tokenHeader is bearer_token this is 64bit encoded token
        if(tokenHeader!= null && tokenHeader.startsWith("Bearer ")){//this part of string i remove bearer ,i will require only token then here i do it.
            String token=tokenHeader.substring(8,tokenHeader.length()-1);
            System.out.println(token);
        }
        filterChain.doFilter(request,response);
    }//i am calling this becoz subsequent request it is filter

}