package com.revature.georgeproject.controllers;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.revature.georgeproject.models.Field;
import com.revature.georgeproject.models.File;
import com.revature.georgeproject.models.Record;
import com.revature.georgeproject.services.FileService;
import org.bson.Document;
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
    public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file) throws IOException {
        File f = fileService.addFile(file);
        return ResponseEntity.ok(fileService.readAllBytes(file));
    }

    @GetMapping("/file/all")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/file/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<File> getFile(@PathVariable String id) {
        File f = fileService.getFile(id);

        return ResponseEntity.ok(f);
    }

    @GetMapping("/file/{id}/contents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getContents(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(fileService.readContents(fileService.getFile(id)));
    }

    @GetMapping("/records/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> getAllRecordsByFileName(@PathVariable String name) {
        return ResponseEntity.ok(fileService.getAllRecordsByFlatFile(name));
    }

    @PostMapping("/file/parse")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Record>> parse(@RequestParam("file") String file, @RequestParam("spec") String spec) throws IOException {
        File flatFile = fileService.getFileByName(file);
        String flatName = flatFile.getFileName();
        File specFile = fileService.getFileByName(spec);
        String specName = specFile.getFileName();
        return ResponseEntity.ok(fileService.getAllRecordsByFlatAndSourceFile(flatName, specName));
    }

//    @PostMapping("/file/parse")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Record> parse(@RequestParam("file") String file, @RequestParam("spec") String spec) throws IOException {
//        File flatFile = fileService.getFileByName(file);
//        File specFile = fileService.getFileByName(spec);
//        return fileService.storeRecords(flatFile, specFile);
//    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(@RequestParam("file") MultipartFile file,@RequestParam("spec") MultipartFile spec) throws IOException {
//        Map<String, Field> map = fileService.parseSpecFields(spec);
        fileService.storeRecords(file, spec);
        return "ok";
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String fileAlreadyExists(MongoWriteException e) {
        return e.getMessage();
    }

}
