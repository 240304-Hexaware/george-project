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

    @Field("sourceFile")
    private String sourceFile;

    @Field("fields")
    private org.bson.Document fields;

    public Record() {
    }

    public Record(String sourceFile, org.bson.Document fields) {
        this.sourceFile = sourceFile;
        this.fields = fields;
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

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public org.bson.Document getFields() {
        return fields;
    }

    public void setFields(org.bson.Document fields) {
        this.fields = fields;
    }
}
