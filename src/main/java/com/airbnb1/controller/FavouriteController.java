package com.airbnb1.controller;

import com.airbnb1.entity.Favourite;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.FavouriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {// i will not supply the property id in the URL rather
    //let us supply the nested object then more easy for the angular code do that
    private FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }
    @PostMapping
    public ResponseEntity<Favourite> addFavourite(
            @RequestBody Favourite favourite,
            @AuthenticationPrincipal PropertyUser user //we know that which user is actually setting the value true //this will take a current session
            ){
        favourite.setPropertyUser(user);
        Favourite savedFavourite = favouriteRepository.save(favourite);//before i save the favourite on thing user object need to be set
        return new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);
    }
}
