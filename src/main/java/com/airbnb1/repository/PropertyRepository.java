package com.airbnb1.repository;

import com.airbnb1.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    //propertyRepository return back property details
    //this will return back a list of properties
    //here i use joins in my project
    @Query("select p from Property p JOIN Location l on p.location=l.id JOIN Country c on p.country=c.id where l.locationName=:locationName")//what is p here is a property class and what consists of variables like id ,consits of property name,location..etc
    List<Property> findPropertyByLocation(@Param("locationName") String locationName);
}