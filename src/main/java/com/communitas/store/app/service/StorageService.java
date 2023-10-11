package com.communitas.store.app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile multipartFile);

    Resource loadAsResource(String filename);

    void delete(String filename);
}
