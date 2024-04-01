package com.revature.georgeproject.models;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("records")
public class Record {

    @MongoId
    private String id;

    @CreatedDate
    @Field("createdDate")
    private Date createdDate;

    @Field("uploadedBy")
    private String username = "test";

    @Field("flatSourceFile")
    private String flatSourceFile;

    @Field("specSourceFile")
    private String specSourceFile;

    @Field("fields")
    private org.bson.Document fields;

    public Record() {
    }

    public Record(String flatSourceFile, String specSourceFile, org.bson.Document fields) {
        this.flatSourceFile = flatSourceFile;
        this.specSourceFile = specSourceFile;
        this.fields = fields;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFlatSourceFile() {
        return flatSourceFile;
    }

    public void setFlatSourceFile(String flatSourceFile) {
        this.flatSourceFile = flatSourceFile;
    }

    public String getSpecSourceFile() {
        return specSourceFile;
    }

    public void setSpecSourceFile(String specSourceFile) {
        this.specSourceFile = specSourceFile;
    }

    public org.bson.Document getFields() {
        return fields;
    }

    public void setFields(org.bson.Document fields) {
        this.fields = fields;
    }
}
