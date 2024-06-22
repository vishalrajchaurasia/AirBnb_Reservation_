package com.airbnb1.controller;

import com.airbnb1.entity.Booking;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBooking(
            @RequestBody Booking booking,//in this case booking details itself be capture the property details
            @AuthenticationPrincipal PropertyUser user

            ){
        booking.setPropertyUser(user);
        Booking createdBooking = bookingRepository.save(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
