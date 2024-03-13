package com.revature.georgeproject.services;

import com.revature.georgeproject.models.File;
import com.revature.georgeproject.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File addFile(String name, String type, byte[] contents) throws IOException{
        File file = new File(name, type, contents);
        fileRepository.insert(file);
        return file;
    }

    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }

}
