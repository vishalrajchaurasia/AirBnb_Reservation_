package com.airbnb1.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName) {//this MultipartFile is standard procedure to upload a file
        if (file.isEmpty()) {//the datatype of the variable is multipart when you upload any file
            throw new IllegalStateException("Cannot upload empty file");//if the file is empty it give this exception
        }
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());//this is our file concept this is java concept
            file.transferTo(convFile);//this method is convert file to some binary,  and it is copy from local system
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);//this is upload a file in to a AWS
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();//and this is return back to object URL ,file is uploaded and get this url
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }


        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }
    //Deleted method
    // public String deleteBucket(String bucketName) {
//        amazonS3.deleteBucket(bucketName);
//        return "File is deleted";
//    }
}