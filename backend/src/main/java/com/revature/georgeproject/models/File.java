package com.revature.georgeproject.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Arrays;

@Document("files")
public class File {
    @MongoId
    private String id;

    @Field("fileName")
    private String fileName;

    @Field("fileType")
    private String fileType;

    @Field("contents")
    private byte[] contents;

    public File() {
    }

    public File(String fileName, String fileType, byte[] contents) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }


}
