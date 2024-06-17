package com.airbnb1.controller;

import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.entity.Review;
import com.airbnb1.repository.PropertyRepository;
import com.airbnb1.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    //now save the content
    private ReviewRepository reviewRepository;

    //get the property details i reqired
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,//property id is come from URL
            @RequestBody Review review,//review is come from ReviewDto
            @AuthenticationPrincipal PropertyUser user //user is given the review for particular a particular property //it is automatically fetch the details user currently logged in
    ){//current user logged in will give the review i can find the current user used in @AuthenticationPrincipal
        Review r= reviewRepository.findReviewByUserIdAndPropertyId(propertyId, user.getId());
        if(r!=null){
            return new ResponseEntity<>("You have already reviewed this property", HttpStatus.BAD_REQUEST);
        }

        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        review.setProperty(property);
        review.setPropertyUser(user);

        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }
}
