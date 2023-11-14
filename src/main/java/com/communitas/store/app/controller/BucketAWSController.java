package com.communitas.store.app.controller;


import com.communitas.store.app.response.AssetResponse;
import com.communitas.store.app.service.AWSS3Services;
import com.sun.mail.iap.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/bucket-s3")
public class BucketAWSController {

    @Autowired
    private AWSS3Services awss3Services;

    @PostMapping("/upload")
    Map<String, String> upload(@RequestParam("file")  MultipartFile file){
        String key = null;
        try {
            key = awss3Services.uploadFile(file);
            Map<String, String> result = new HashMap<>();
            result.put("key", key);
            result.put("url", awss3Services.getObjectURL(key));
            return result;

        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen",e);
        }
    }

    @GetMapping("/show-file/{key}")
    public ResponseEntity<ByteArrayResource> getObject(@PathVariable String key) {
        AssetResponse assetResponse = awss3Services.getObject(key);
        ByteArrayResource byteArrayResource = new ByteArrayResource(assetResponse.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", assetResponse.getContentType());
        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }
}
