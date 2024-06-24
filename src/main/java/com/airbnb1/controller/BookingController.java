package com.airbnb1.controller;

import com.airbnb1.dto.BookingDto;
import com.airbnb1.entity.Booking;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.BookingRepository;
import com.airbnb1.repository.PropertyRepository;
import com.airbnb1.service.BucketService;
import com.airbnb1.service.PDFService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;//because i want a property price
    private PDFService pdfService;
    private BucketService bucketService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, PDFService pdfService, BucketService bucketService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
    }
    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<?> createBooking( //of the type <Booking>, this will automatically return back user details,propertyDetails and the booking deatils
            @RequestBody Booking booking,//in this case booking details itself be capture the property details
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable long propertyId

            ) throws IOException {
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
        boolean b = pdfService.generatePDF("C://air_bnb_reservation 1//" + "booking-confirmation-id" + createdBooking.getId() + ".pdf", dto);//this is became a fileName
        if(b){
            //Upload your file into bucket
            MultipartFile file = BookingController.convert("C://air_bnb_reservation 1//" + "booking-confirmation-id" + createdBooking.getId() + ".pdf");
            bucketService.uploadFile(file,"myairbnb4");//now file will upload into bucket means AWS s3 bucket
        }else {
            return new ResponseEntity<>("Something went Wrong ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
    public static MultipartFile convert(String filePath) throws IOException {
        //load the file from the specified path
      File file = new File(filePath);

      //read the file contents into a byte array
      byte[] fileContent = Files.readAllBytes(file.toPath());

      //convert byte arrays to a Resource(byteArrayResource)
        Resource resource= new ByteArrayResource(fileContent);
        //create MultipartFile from Resource
        MultipartFile multipartFile= new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }
            @Override
            public String getOriginalFilename() {
                return file.getName();
            }
            @Override
            public String getContentType() {
                return null; //you can set appropriate content type here
            }
            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }
            @Override
            public long getSize() {
                return fileContent.length;
            }
            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }
            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }
            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(),fileContent);
            }
        };
        return multipartFile;
    }

}
