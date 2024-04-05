import { CommonModule } from '@angular/common';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button'
import {MatTableDataSource, MatTableModule} from '@angular/material/table'
import {MatSort, MatSortModule} from '@angular/material/sort'
import { MongoFile } from '../mongo-file';
import { FileService } from '../file.service';
import { Record } from '../record';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { UserService } from '../user.service';


@Component({
  selector: 'app-file',
  standalone: true,
  imports: [MatInputModule, MatCardModule, MatGridListModule, CommonModule, FormsModule, MatInputModule, MatSelectModule, MatFormFieldModule, MatButtonModule, MatTableModule, MatSortModule],
  templateUrl: './file.component.html',
  styleUrl: './file.component.css'
})
export class FileComponent {

  httpClient : HttpClient;
  file : File | undefined;
  downloadFile : Blob | undefined;
  fileName : string; 
  specFiles: MongoFile[] = []; 
  flatFiles : MongoFile[] = [];
  records : Record[] = [];
  fields : any[] = [];
  fieldList : string[] = [];
  spec : string = "";
  flat : string = "";
  showRecords : boolean = false;
  succesfulUpload : boolean = false;
  dataSource : any;
  currentUser : string = '';


  constructor(httpClient : HttpClient, private fileService : FileService, private userService: UserService) {
    this.httpClient = httpClient;
    this.fileName = "";
    this.userService.loggedIn$.subscribe(data =>{
      this.currentUser = data.username;
    })
  }

  @ViewChild(MatSort) sort : MatSort = new MatSort();

  ngOnInit() {
    this.flatFiles = [];
    this.specFiles = [];
    let arr : MongoFile[] = [];
    this.fileService.getAllFiles().subscribe({
      next : (data : any) => {
        data.forEach((file : MongoFile) => {
          // console.log(file);
          arr.push(file);
        })
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        console.log("Response complete");
        arr.forEach((file) =>{
          // console.log("on init")
          if(file.fileType == "flat") {
            this.flatFiles.push(file);
          } else if (file.fileType == "specification") {
            this.specFiles.push(file);
          }
        })
      }
    });
  }


  updateFiles() {
    let arr : MongoFile[] = [];
    this.flatFiles = [];
    this.specFiles = [];
    this.fileService.getAllFiles().subscribe({
      next : (data : any) => {
        data.forEach((file : MongoFile) => {
          arr.push(file);
        })
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        console.log("Response complete");
        arr.forEach((file) =>{
          // console.log("File ", file);
          if(file.fileType == "flat") {
            this.flatFiles.push(file);
          } else if (file.fileType == "specification") {
            this.specFiles.push(file);
          }
        })
      }
    });
  }
  
  

  parse() {
    this.showRecords = true;
    this.fields = [];
    this.fieldList = [];
    this.records = [];
    this.fileService.postParsedRecords(this.flat, this.spec).subscribe({
      next : (data : any) => {
        console.log(data)
        data.forEach((record : any) => {
          this.records.push(record);
        })
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        console.log("Response complete");
        this.fields = this.fileService.getFields(this.records);
        this.dataSource = new MatTableDataSource(this.fields);
        this.dataSource.sort = this.sort;
        this.fieldList = this.fileService.getFieldList(this.fields);
      }
    })

  }

  fileSelected(event : any) {
    this.file = event.target.files[0];
    console.log("File selected: ",  this.file);
  }

  upload() {
    if (this.file == undefined) {
      alert("No file selected");
      return;
    }

    let form : FormData = new FormData();
    form.append("file", this.file);

    let url : string = "http://localhost:8080/api/file";
    let options : any = {
      observe: "response",
      responseType: 'text',
      headers: {
        contentType : this.file.type,
        username: this.currentUser//for our trusting system
      }
    };

    let response = this.httpClient.post(url, form, options);
    response.subscribe({
      next : (data : any) => {
        let contentType : any = options.headers.contentType;
        let fileBody : string | null = data.body;
        if (contentType == "") {
          contentType = "application/octet-stream";
        }
        this.downloadFile = new Blob([fileBody as string], {type : contentType});
        this.succesfulUpload = true;
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        // console.log(error.status === 409);
        alert(error.message);
      },
      complete : () => {
        console.log("Response complete");
      }
    });
  }

  saveFile() {
    if (this.downloadFile == undefined) {
      alert("No file to download");
      return;
    }

    const linkElement = document.createElement("a");
    linkElement.href = URL.createObjectURL(this.downloadFile);
    linkElement.download = this.fileName;
    linkElement.click();
    linkElement.remove();
  }

}
