package com.airbnb1.controller;

import com.airbnb1.dto.BookingDto;
import com.airbnb1.entity.Booking;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.BookingRepository;
import com.airbnb1.repository.PropertyRepository;
import com.airbnb1.service.PDFService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;//because i want a property price
    private PDFService pdfService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, PDFService pdfService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
    }
    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<Booking> createBooking( //of the type <Booking>, this will automatically return back user details,propertyDetails and the booking deatils
            @RequestBody Booking booking,//in this case booking details itself be capture the property details
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable long propertyId

            ){
        booking.setPropertyUser(user);
//        Property property = booking.getProperty();//what will do line it go to the booking class it will help me the get the property object address supply here @RequestBody Booking booking
//        Long propertyId = property.getId();
//        Property completePropertyInfo = propertyRepository.findById(propertyId).get();
//        Booking createdBooking = bookingRepository.save(booking); //this is the one way of doing it for above four lines.
        Property property = propertyRepository.findById(propertyId).get();// this another way of doing // you got the property id findById(propertyId)
        int propertyPrice = property.getNightlyPrice();
        int totalNights = booking.getTotalNights();
        int totalPrice = propertyPrice * totalNights;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);
        Booking createdBooking = bookingRepository.save(booking);

        BookingDto dto= new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propertyPrice);//now price is coming from propertyPrice object
        dto.setTotalPrice(createdBooking.getTotalPrice());
        //after booking confirmation i call that here //ONCE A booking is done
        //create PDF with Booking confirmation
        pdfService.generatePDF("C://air_bnb_reservation 1//"+"booking-confirmation-id"+createdBooking.getId()+".pdf",dto);//this is became a fileName


        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

}
