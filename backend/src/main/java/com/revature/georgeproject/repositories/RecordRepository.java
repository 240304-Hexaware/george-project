package com.revature.georgeproject.repositories;

import com.revature.georgeproject.models.Record;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends MongoRepository<Record, ObjectId> {
    List<Record> findByFlatSourceFile(String fileName);

    List<Record> findBySpecSourceFile(String fileName);

    List<Record> findByFlatSourceFileAndSpecSourceFile(String flatSourceFile, String specSourceFile);

    List<Record> findByUsername(String user);
}
