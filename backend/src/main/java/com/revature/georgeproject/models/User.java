package com.revature.georgeproject.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Document("users")
public class User {

    @MongoId
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

}
