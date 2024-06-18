package com.airbnb1.repository;

import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.property = :property and r.propertyUser = :user")
    Review findReviewByUser(@Param("property") Property property,@Param("user") PropertyUser user);

    //get all the review
    List<Review> findByPropertyUser(PropertyUser user);


}
