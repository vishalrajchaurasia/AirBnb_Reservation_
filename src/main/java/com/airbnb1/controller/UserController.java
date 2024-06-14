package com.airbnb1.controller;

import com.airbnb1.dto.LoginDto;
import com.airbnb1.dto.PropertyUserDto;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/addUser")//request mapping can do all crud operation in all API
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){ //i will take the here propertyUserDto //we do not froget to write @RequestBody
        //another wise JSON content will not go this PropertyUserDto
        PropertyUser propertyUser = userService.addUser(propertyUserDto);
        if(propertyUser!=null){
            return new ResponseEntity<>("Registration is Successful ", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went Wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);
        if (token!=null) {
            return new ResponseEntity<>( token,HttpStatus.OK);
        }
        return new ResponseEntity<>("in valid credentials ",HttpStatus.UNAUTHORIZED);
    }
//Note-After return a token 2nd time at subsequent request that i make it should first validate a token and only then process the request how do we do this now
//four thing-algorithm,secrets key, issuers key and expiry time.
}

