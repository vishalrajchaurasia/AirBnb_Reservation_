package com.airbnb1.service;

import com.airbnb1.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")//what does value annotaion is does read the details from properties files and directly initialize the variable
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private  String issuer; //some client send the token then verify,hacker cannot create dummy token
    @Value("${jwt.expiry.duration}")
    private int expiryTime; //expiry time is calculated in miliseconds.

    private Algorithm algorithm;
    // i want to inject the value in it.hardcoding value is injected a create a problem

    private final static String USER_NAME="username";
    @PostConstruct //what is postConstruct annotation does when you start application it will start automatically
    public void  postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);

    }
    public String generateToken(PropertyUser propertyUser){
        return JWT.create().
                withClaim(USER_NAME,propertyUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm); //algorithm+secret key both come here
    }//this above line build the token
    public String getUserName(String token){
        DecodedJWT decodedJWT =JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
