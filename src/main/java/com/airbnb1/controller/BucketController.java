package com.airbnb1.controller;
import com.airbnb1.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("s3bucket")
@CrossOrigin("*")//CrossOrigin is access API in angular ,this is from anywhere use it
public class BucketController {//this is not a BucketController its a imageBucketController

    @Autowired
    BucketService service;

    @PostMapping(path = "/upload/file/{bucketName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file,
                                             @PathVariable String bucketName) {
        return new ResponseEntity<>(service.uploadFile(file,bucketName), HttpStatus.OK);
    }

//@DeleteMapping(path="/delete/file/{bucketName}/{fileName}")
//public ResponseEntity<String> deleteFile(@PathVariable String bucketName,@PathVariable String fileName)
//{
//    return new ResponseEntity<>(service.deleteFile(bucketName,fileName),HttpStatus.OK);
//}
}
