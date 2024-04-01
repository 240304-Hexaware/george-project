export interface Record {
    _id : String;
    createdDate : Date;
    uploadedBy : String;
    flatSourceFile : String;
    specSourceFile : String;
    fields : Object[];
    _class : String;
}
