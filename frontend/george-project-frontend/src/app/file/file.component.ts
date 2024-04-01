import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button'
import { MongoFile } from '../mongo-file';


@Component({
  selector: 'app-file',
  standalone: true,
  imports: [FormsModule, MatInputModule, MatSelectModule, MatFormFieldModule, MatButtonModule],
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
  spec : string = "";
  flat : string = "";


  constructor(httpClient : HttpClient) {
    this.httpClient = httpClient;
    this.fileName = "";
  }

  ngOnInit() {
    let url : string = "http://localhost:8080/api/file/all";
    let response = this.httpClient.get(url)
    response.subscribe({
      next : (data : any) => {
        data.forEach((file : MongoFile) => {
          if (file.fileType == "flat") {
            this.flatFiles.push(file);
          } else if (file.fileType == "specification") {
            this.specFiles.push(file);
          }
        })
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        // console.log(this.files);
        console.log("Response complete");
      }
    })
  }

  parse() {
    console.log("flat ", this.flat);
    console.log("spec ", this.spec);
    let url : string = "http://localhost:8080/api/file/parse";
    const params = new HttpParams()
      .set("file", this.flat)
      .set("spec", this.spec);
    
    let options : any = {
      params : params
    }
    let response = this.httpClient.post(url, null, options)
    response.subscribe({
      next : (data : any) => {
        console.log(data)
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        console.log("Response complete");
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
        username: "gahanwang"//for our trusting system
      }
    };

    let response = this.httpClient.post(url, form, options);
    response.subscribe({
      next : (data : any) => {
        // let headers = data.headers;
        // let keys = headers.keys();
        // for (let key of keys) {
        //   console.log(key, headers.get(key));
        // }
        let contentType : any = options.headers.contentType;
        let fileBody : string | null = data.body;
        if (contentType == "") {
          contentType = "application/octet-stream";
        }
        this.downloadFile = new Blob([fileBody as string], {type : contentType});
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
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
