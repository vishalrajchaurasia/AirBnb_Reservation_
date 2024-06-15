package com.airbnb1.config;

import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.PropertyUserRepository;
import com.airbnb1.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component //handover this class to spring boot its bean lifecycle management
public class JWTRequestFilter extends OncePerRequestFilter {//this abstract class is incomplete method to doFilterInternal method
    //OncePerRequestFilter request is abstract class.
    private JWTService jwtService;
    private PropertyUserRepository userRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //all the incoming request access with automatically
        String tokenHeader = request.getHeader("Authorization");//is not just a token i call that tokenHeader is bearer_token this is 64bit encoded token
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){//this part of string i remove bearer ,i will require only token then here i do it.
            String token=tokenHeader.substring(8,tokenHeader.length()-1);
            String username = jwtService.getUserName(token);//take this username into this variable
            Optional<PropertyUser> opUser = userRepository.findByUsername(username);//and get Optional USer
            if(opUser.isPresent()){
                PropertyUser user = opUser.get();
                UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //token is taking from http request but are the validating the token no.
        //let us verify the token that is coming authenticated URL

        filterChain.doFilter(request,response);
    }//i am calling this becoz subsequent request it is filter

}