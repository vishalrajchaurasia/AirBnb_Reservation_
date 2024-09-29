package com.airbnb1.controller;

import com.airbnb1.dto.LoginDto;
import com.airbnb1.dto.PropertyUserDto;
import com.airbnb1.dto.TokenResponse;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){//response is here for two different type then put (?),it means method can return any kind of value
        String token = userService.verifyLogin(loginDto);
        if (token!=null) {
            // i create here tokenResponse Payload
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>( tokenResponse,HttpStatus.OK);//not send back the token in String like that String token = userService.verifyLogin(loginDto);
        }
        return new ResponseEntity<>("Invalid credentials ",HttpStatus.UNAUTHORIZED);
        //Note-After return a token 2nd time at subsequent request that i make it should first validate a token and only then process the request how do we do this now
//four thing-algorithm,secrets key, issuers key and expiry time.
    }
//how do you know which current user has been logged in.i want a current get logged user Profile
    @GetMapping("/profile") //I have here profile URL which an admin and USER both they should be able to access
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user) {
        return user;
    }


}

