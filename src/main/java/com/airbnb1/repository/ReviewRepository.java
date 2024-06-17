package com.airbnb1.repository;

import com.airbnb1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.property.id = :propertyId and r.propertyUser.id = :userId")
    Review findReviewByUserIdAndPropertyId(@Param("userId") long userId, @Param("propertyId") long propertyId);
}
