package com.airbnb1.controller;

import com.airbnb1.entity.Property;
import com.airbnb1.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    //i will not use a service layer but further we will refractor the code and we will put this to a service layer
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("{locationName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locationName){
        List<Property> properties = propertyRepository.findPropertyByLocation(locationName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}
