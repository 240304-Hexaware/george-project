package com.revature.georgeproject.repositories;

import com.revature.georgeproject.models.File;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends MongoRepository<File, ObjectId> {
    File findByFileName(String fileName);
    List<File> findByFileType(String fileType);
}
