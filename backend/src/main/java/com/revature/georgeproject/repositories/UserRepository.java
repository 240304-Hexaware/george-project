package com.revature.georgeproject.repositories;

import com.revature.georgeproject.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsernameAndPassword(String username, String password);

}
