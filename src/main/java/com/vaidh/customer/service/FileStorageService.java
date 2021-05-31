package com.vaidh.customer.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {
    public String save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

}
