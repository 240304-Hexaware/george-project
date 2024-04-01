package com.revature.georgeproject.repositories;

import com.revature.georgeproject.models.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<File, String> {
    public File findByFileName(String fileName);
}
