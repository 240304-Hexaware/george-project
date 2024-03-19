package com.revature.georgeproject.repositories;

import com.revature.georgeproject.models.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<Record, String> {
}
