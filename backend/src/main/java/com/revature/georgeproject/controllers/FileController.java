package com.revature.georgeproject.controllers;

import com.revature.georgeproject.models.Field;
import com.revature.georgeproject.models.File;
import com.revature.georgeproject.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

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
        File f = fileService.addFile(file);
        return ResponseEntity.ok(f);
    }

    @GetMapping("/file/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<File> getFile(@PathVariable String id) {
        File f = fileService.getFile(id);

        return ResponseEntity.ok(f);
    }

    @GetMapping("/file/{id}/contents")
    @ResponseStatus(HttpStatus.OK)
    public String getContents(@PathVariable String id) throws IOException {
        return fileService.readContents(fileService.getFile(id));
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(@RequestParam("file") MultipartFile file,@RequestParam("spec") MultipartFile spec) throws IOException {
//        Map<String, Field> map = fileService.parseSpecFields(spec);
        fileService.storeRecords(file, spec);
        return "ok";
    }



}
