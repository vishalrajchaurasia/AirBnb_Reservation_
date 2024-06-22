package com.airbnb1.controller;

import com.airbnb1.entity.Images;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.repository.ImagesRepository;
import com.airbnb1.repository.PropertyRepository;
import com.airbnb1.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {//once the image is uploaded we should get the URL and save in to the database.
    private ImagesRepository imagesRepository;
    //now when you upload a images and you also have supply the property id no.
    //you can also supply the nested object or the property  id no.
    private PropertyRepository propertyRepository;
    private BucketService bucketService;

    public ImageController(ImagesRepository imagesRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                             @PathVariable String bucketName,
                                             @PathVariable long propertyId,
                                             @AuthenticationPrincipal PropertyUser user//this will give a user details who uploaded a images.

    ) {
        String imageUrl = bucketService.uploadFile(file, bucketName);//this will help me upload a images
        Property property = propertyRepository.findById(propertyId).get();
        Images img = new Images();//that is our entity class name
        img.setImageUrl(imageUrl);
        img.setProperty(property);
        img.setPropertyUser(user);

        Images savedImage = imagesRepository.save(img);


        return new ResponseEntity<>(savedImage, HttpStatus.OK);
    }
}
