package com.vaidh.customer.controller;

import com.vaidh.customer.dto.FileResponseMessage;
import com.vaidh.customer.dto.ResponseMessage;
import com.vaidh.customer.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    Logger logger =  LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileStorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String url = storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new FileResponseMessage(message, url));
        } catch (Exception e) {
            logger.error("ERROR on uopload :: " + e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileResponseMessage(message, ""));
        }
    }

    @GetMapping("/load-file")
    public ResponseEntity<Resource> loadFile(@RequestParam String fileName) {
        String message = "";
        try {
            Resource file = storageService.load(fileName);

           // message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);

        } catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
