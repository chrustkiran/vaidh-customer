package com.vaidh.customer.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final Path root = Paths.get("uploads");
    private final String fireStorageUrl = "https://firebasestorage.googleapis.com/v0/b/vaidh-d7457.appspot.com/o/%s?alt=media";

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            return saveFileOnFirebase(file);
          /*  if (!Files.exists(root)) {
                init();
            }
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            return file.getOriginalFilename();*/
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public String load(String filename) {
        try {
            LOGGER.info("request at load :: file-storage-service :: filename ", filename);
            return String.format(fireStorageUrl, filename);
           /* Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }*/
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    private String saveFileOnFirebase(MultipartFile multipartFile) throws IOException, FirebaseAuthException {
        String name = generateFileName(multipartFile.getName());
        StorageClient.getInstance().bucket().create(name, multipartFile.getBytes(), multipartFile.getContentType());

        LOGGER.info("File " + multipartFile.getOriginalFilename() + " uploaded to bucket " + " as " + name);

        LOGGER.info(
                "genetated auth token " + FirebaseAuth.getInstance().createCustomToken(UUID.randomUUID().toString())
        );
       
        return String.format(fireStorageUrl, name);
    }

    private String generateFileName(String name) {
        return new Date().getTime() + "-" + Objects.requireNonNull(name).replace(" ", "_");
    }
}
