package com.revature.georgeproject.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.georgeproject.models.Field;
import com.revature.georgeproject.models.File;
import com.revature.georgeproject.models.Record;
import com.revature.georgeproject.repositories.FileRepository;
import com.revature.georgeproject.repositories.RecordRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private RecordRepository recordRepository;

    public File addFile(MultipartFile file, String username) throws IOException {
        String path = Paths.get(System.getProperty("user.dir"), "backend/src/main/resources/static", file.getOriginalFilename()).toString();
        File uploadFile = new File(file.getOriginalFilename(), file.getSize(), file.getContentType());
        if (("application/json").equals(file.getContentType())) {
            uploadFile.setFileType("specification");
        } else {
            uploadFile.setFileType("flat");
        }
        uploadFile.setContents(file.getBytes());
        uploadFile.setFilePath(path);
        uploadFile.setUsername(username);
        java.io.File f = new java.io.File(path);
        try (OutputStream o = new FileOutputStream(f)) {
            o.write(file.getBytes());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


        fileRepository.insert(uploadFile);
        return uploadFile;
    }

    public String readContents(File file) {
        return new String(file.getContents()).intern();
    }

    public String readAllBytes(MultipartFile file) throws IOException {
        return new String(file.getBytes()).intern();
    }

    public Map<String, Field> parseSpecFields(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Field> map = mapper.readValue(readAllBytes(file), new TypeReference<Map<String, Field>>() {});
        for (String s : map.keySet()) {
            map.get(s).setName(s);
        }
        return map;
    }

    public Map<String, Field> parseSpecFields(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Field> map = mapper.readValue(readContents(file), new TypeReference<Map<String, Field>>() {});
        for (String s : map.keySet()) {
            map.get(s).setName(s);
        }
        return map;
    }

    public List<Record> storeRecords(MultipartFile file, MultipartFile specFile, String username) throws IOException {
        Map<String, Field> spec = parseSpecFields(specFile);
        List<Record> records = new ArrayList<>();
        String contents = readAllBytes(file);
        System.out.println(contents.length());
        Set<String> fields = spec.keySet();
        int startIndex = 0;
        int endIndex = 0;
        int index = 0;
        while(index < contents.length()) {
            Document document = new Document();
            for(String fieldName : fields) {
                Field field = spec.get(fieldName);
                startIndex = field.getStartIndex() + index;
                endIndex = field.getEndIndex() + index;
                String fieldValue = contents.substring(startIndex , endIndex + 1).trim();
                document.put(fieldName, fieldValue);
            }
            Record rec = new Record(file.getOriginalFilename(), specFile.getOriginalFilename(), document);
            rec.setUsername(username);
            recordRepository.insert(rec);
            records.add(rec);
            index = endIndex + 1;
        }
    return records;
    }

    public List<Record> storeRecords(File file, File specFile, String username) throws IOException {
        if (!(getAllRecordsByFlatAndSourceFile(file.getFileName(),specFile.getFileName()).isEmpty())) {
            System.out.print("im here");
            return getAllRecordsByFlatAndSourceFile(file.getFileName(),specFile.getFileName());
        }
        List<Record> records = new ArrayList<>();
        Map<String, Field> spec = parseSpecFields(specFile);
        String contents = readContents(file);
        System.out.println(contents.length());
        Set<String> fields = spec.keySet();
        int startIndex = 0;
        int endIndex = 0;
        int index = 0;
        int oneRecordLength = 0;
        while(index < contents.length() && index + oneRecordLength < contents.length())  {
            Document document = new Document();
            for(String fieldName : fields) {
                Field field = spec.get(fieldName);
                startIndex = field.getStartIndex() + index;
                endIndex = field.getEndIndex() + index;
                String fieldValue = contents.substring(startIndex , endIndex + 1).trim();
                document.put(fieldName, fieldValue);
            }
            if (index == 0) {
                oneRecordLength = endIndex + 1;
                System.out.println("rec length" + oneRecordLength);
            }
            Record rec = new Record(file.getFileName(), specFile.getFileName(),document);
            rec.setUsername(username);
            recordRepository.save(rec);
            records.add(rec);
            index = endIndex + 1;
            System.out.println(index);
        }
    return records;
    }

    public File getFile(ObjectId id) {
        return fileRepository.findById(id).get();
    }

    public File getFileByName(String name) {
        return fileRepository.findByFileName(name);
    }
    public List<File> getAllFiles() { return fileRepository.findAll();}

    public List<File> getAllFlatFiles() { return fileRepository.findByFileType("flat");}
    public List<File> getAllSpecFiles() { return fileRepository.findByFileType("specification");}

    public List<Record> getAllRecords() {return recordRepository.findAll();}
    public List<Record> getAllRecordsByFlatFile(String fileName) {
        return recordRepository.findByFlatSourceFile(fileName);
    }

    public List<Record> getAllRecordsBySpecFile(String fileName) {
        return recordRepository.findBySpecSourceFile(fileName);
    }

    public List<Record> getAllRecordsByFlatAndSourceFile(String flat, String spec) {
        return recordRepository.findByFlatSourceFileAndSpecSourceFile(flat, spec);
    }

    public List<Record> getAllRecordsByUser(String user) {
        return recordRepository.findByUsername(user);
    }
}
