export interface MongoFile {
    _id : String;
    fileName : String;
    contentType : String;
    fileType : String;
    fileSize : number;
    uploadDate : Date;
    uploadedBy: String;
    contents : any;
    _class : String;

}
