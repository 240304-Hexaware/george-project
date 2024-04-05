package com.revature.georgeproject.controllers;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.revature.georgeproject.models.Field;
import com.revature.georgeproject.models.File;
import com.revature.georgeproject.models.Record;
import com.revature.georgeproject.services.FileService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class FileController {

    private FileService fileService;


    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file, @RequestHeader String username) throws IOException {
        File f = fileService.addFile(file, username);
        return ResponseEntity.ok(fileService.readAllBytes(file));
    }

    @GetMapping("/file/all")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/file/flat")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<File>> getAllFlatFiles() {
        return ResponseEntity.ok(fileService.getAllFlatFiles());
    }

    @GetMapping("/file/spec")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<File>> getAllSpecFiles() {
        return ResponseEntity.ok(fileService.getAllSpecFiles());
    }

    @GetMapping("/file/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<File> getFile(@PathVariable ObjectId id) {
        File f = fileService.getFile(id);

        return ResponseEntity.ok(f);
    }

    @GetMapping("/file/{id}/contents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getContents(@PathVariable ObjectId id) throws IOException {
        return ResponseEntity.ok(fileService.readContents(fileService.getFile(id)));
    }

    @GetMapping("/records")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecords() {
        return ResponseEntity.ok(fileService.getAllRecords());
    }

    @GetMapping(value = "/records/", params = "flat")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecordsByFlatFile(@RequestParam("flat") String flat) {
        return ResponseEntity.ok(fileService.getAllRecordsByFlatFile(flat));
    }

    @GetMapping(value = "/records/", params = "spec")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecordsBySpecFile(@RequestParam("spec") String spec) {
        return ResponseEntity.ok(fileService.getAllRecordsBySpecFile(spec));
    }

    @GetMapping(value = "/records/", params = "user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecordsByUser(@RequestParam("user") String user) {
        return ResponseEntity.ok(fileService.getAllRecordsByUser(user));
    }


    @GetMapping("/records/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecordsByFlatAndSpecFile(@RequestParam("flat") String flat, @RequestParam("spec") String spec) throws IOException {
        return ResponseEntity.ok(fileService.getAllRecordsByFlatAndSourceFile(flat, spec));
    }

    @PostMapping("/file/parse")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> parse(@RequestParam("flat") String file, @RequestParam("spec") String spec, @RequestHeader String username) throws IOException {
        File flatFile = fileService.getFileByName(file);
        File specFile = fileService.getFileByName(spec);
        return ResponseEntity.ok(fileService.storeRecords(flatFile, specFile, username));
    }

//    @PostMapping("/file/parse")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Record> parse(@RequestParam("file") String file, @RequestParam("spec") String spec) throws IOException {
//        File flatFile = fileService.getFileByName(file);
//        File specFile = fileService.getFileByName(spec);
//        return fileService.storeRecords(flatFile, specFile);
//    }
//
//    @PostMapping("/test")
//    @ResponseStatus(HttpStatus.OK)
//    public String test(@RequestParam("file") MultipartFile file,@RequestParam("spec") MultipartFile spec) throws IOException {
//        fileService.storeRecords(file, spec);
//        return "ok";
//    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String fileAlreadyExists(MongoWriteException e) {
        return e.getMessage();
    }

}
