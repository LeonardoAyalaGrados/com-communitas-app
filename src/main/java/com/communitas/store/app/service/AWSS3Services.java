package com.communitas.store.app.service;


import com.communitas.store.app.response.AssetResponse;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AWSS3Services {
    private final static String BUCKET="communitas-bucket-springboot";

    @Autowired
    private S3Client s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename() != null ?
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")) :
                "";
        String key = generateKey(extension);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType(file.getContentType())
                /*se utiliza para controlar quién tiene acceso a los
                objetos almacenados en S3 y qué tipo de acceso tienen
                * */
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return key;
    }

    private String generateKey(String extension) {
        return UUID.randomUUID().toString() + extension;
    }

    public byte[] downloadFile(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }

    public AssetResponse getObject(String key) {
        // Usar S3Client para obtener el objeto desde S3
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
        try {
            // Leer el contenido del objeto y devolver los bytes
            byte[] bytes = responseInputStream.readAllBytes();
            return new AssetResponse(bytes, responseInputStream.response().contentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getObjectURL(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }
}
