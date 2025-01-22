package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.service.FileService;
import com.tbond.yumdash.service.exception.FileNotFoundException;
import com.tbond.yumdash.service.exception.FileUploadException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${app.upload.dir}")
    private String imageStorageDir;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String contentType = file.getContentType();

            if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new FileUploadException("File not provided or file type is incorrect");
            }

            String fileName = UUID.randomUUID() + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String imagePath = Paths.get(imageStorageDir, fileName).toString();

            Files.write(Paths.get(imagePath), file.getBytes());

            System.out.println(imagePath);

            return fileName;
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    @Override
    public Resource getImage(String filename) {
        try {
            Path filePath = Paths.get(imageStorageDir).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new FileNotFoundException(String.format("File %s not found", filename));
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }


    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
