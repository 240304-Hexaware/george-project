package com.revature.georgeproject.controllers;

import com.revature.georgeproject.models.File;
import com.revature.georgeproject.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    private FileService fileService;


    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @GetMapping("/ping")
//    public String ping() {
//        return "pong!";
//    }

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<File> addFile(@RequestParam("file") MultipartFile file) throws IOException {
        File f = fileService.addFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return ResponseEntity.ok(f);
    }




}
