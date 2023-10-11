package com.communitas.store.app.config;

public class FileStorageProperties extends RuntimeException{

    private String uploadDir;

    public FileStorageProperties() {
    }

    public FileStorageProperties(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

}
