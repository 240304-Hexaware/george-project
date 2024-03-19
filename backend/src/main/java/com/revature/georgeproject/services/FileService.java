package com.revature.georgeproject.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.georgeproject.models.Field;
import com.revature.georgeproject.models.File;
import com.revature.georgeproject.models.Record;
import com.revature.georgeproject.repositories.FileRepository;
import com.revature.georgeproject.repositories.RecordRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private RecordRepository recordRepository;

    public File addFile(MultipartFile file) throws IOException {
        File uploadFile = new File(file.getOriginalFilename(), file.getSize(), file.getContentType());
        if (("application/json").equals(file.getContentType())) {
            uploadFile.setFileType("specification");
            uploadFile.setContents(file.getBytes());
        } else {
            uploadFile.setFileType("flat");
        }
        fileRepository.insert(uploadFile);
        return uploadFile;
    }

    public String readContents(File file) throws IOException {
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

    public void storeRecords(MultipartFile file, MultipartFile specFile) throws IOException {
        Map<String, Field> spec = parseSpecFields(specFile);
        String contents = readAllBytes(file);
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
                System.out.println("Start Index " + startIndex );
                System.out.println("End Index " + endIndex );

                String fieldValue = contents.substring(startIndex , endIndex + 1).trim();
                System.out.println("[" + fieldName + "][" + fieldValue + "]");
                document.put(fieldName, fieldValue);
            }
            System.out.println(document);
            Record record = new Record(file.getOriginalFilename(), document);
            recordRepository.insert(record);
            index += endIndex + 1;
        }

    }

    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }

}
