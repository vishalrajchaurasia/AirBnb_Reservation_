package com.airbnb1.service;

import com.airbnb1.dto.PropertyUserDto;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private PropertyUserRepository userRepository;
    //now do constructor based injection
    public UserService(PropertyUserRepository userRepository) {
        this.userRepository = userRepository;

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
        user.setUserRole(propertyUserDto.getUserRole());
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }

}
