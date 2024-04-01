import { CommonModule } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Record } from '../record';


@Component({
  selector: 'app-records',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './records.component.html',
  styleUrl: './records.component.css'
})
export class RecordsComponent {
  records : Record[] = [];
  fields : any[] = [];
  httpClient : HttpClient;
  fileName : String = '';

  constructor(httpClient : HttpClient) {
    this.httpClient = httpClient;
  }

  view() {
    if (!this.fileName) {
      return;
    }
    this.records = [];
    this.fields = [];
    let url : string = "http://localhost:8080/api/records/" + this.fileName;
    console.log(url);
    let response = this.httpClient.get(url)
    response.subscribe({
      next : (data : any) => {
        data.forEach((item: any) => {
          this.records.push(item);
          // console.log(item);
        });

        this.records.forEach((field)=>{
          console.log(field.fields);
          this.fields.push(field.fields);
        });
      },
      error : (error : HttpErrorResponse) => {
        console.log("Error: ", error);
        alert(error.message);
      },
      complete : () => {
        // console.log(this.records);
        console.log("field " + Object.keys(this.fields.at(0)));
        console.log("Response complete");
      }
    })

  }

  getFields() : any {
    if (this.fields == null) {
      return;
    }
    return Object.keys(this.fields.at(0));
  }

}
