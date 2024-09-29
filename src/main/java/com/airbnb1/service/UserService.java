package com.airbnb1.service;

import com.airbnb1.dto.LoginDto;
import com.airbnb1.dto.PropertyUserDto;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private PropertyUserRepository userRepository;
    //now do constructor based injection
    private JWTService jwtService;

    public UserService(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        //we need to save the password
        PropertyUser user =new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));//here is were set the password in the object should be encrypted there are two way to do it
        //this is one way of encoding with password or what i can do use this new BCryptPasswordEncoder().encode(user.getPassword())
        // what does BCrypt.gensalt(10) this will create a 10 rounds of encryption
        user.setUserRole("ROLE_USER");
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }

    public String verifyLogin(LoginDto loginDto) {//actual value get from database//loginDto this is coming from user
        Optional<PropertyUser> opUser =userRepository.findByUsername(loginDto.getUserName());
        // weather the record is found or not if record is found then data present in it opUser,if not found it will be null.
        if(opUser.isPresent()){//here avoid the null pointer exception is handle now//
            PropertyUser propertyUser = opUser.get(); //PropertyUser propertyUser-this is coming from database//what is the get() method do it is convert opUser to enity object(propertyUser)
            if(BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){//this check password return boolean value
                return jwtService.generateToken(propertyUser);//loginDto.getPassword() is get the raw password //propertyUser.getPassword() is get the encrypted password
            }
        }
        return null;

    }
}
