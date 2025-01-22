package com.tbond.yumdash.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImage(MultipartFile file);

    Resource getImage(String filename);
}
