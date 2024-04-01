package com.revature.georgeproject.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Arrays;
import java.util.Date;

@Document("files")
public class File {
    @MongoId
    private String id;

    @Field("fileName")
    @Indexed(unique = true)
    private String fileName;

    @Field("contentType")
    private String contentType;

    @Field("fileType")
    private String fileType;

    @Field("fileSize")
    private long fileSize;

//    @Field("filePath")
//    private String filePath;

    @CreatedDate
    @Field("uploadDate")
    private Date uploadDate;

    @Field("uploadedBy")
    private String username = "test";

    @Field("contents")
    private byte[] contents;

    public File() {
    }

    public File(String fileName, long fileSize, String contentType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    public File(String fileName, String fileType, long fileSize, byte[] contents, String contentType) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.contents = contents;
        this.contentType = contentType;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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
