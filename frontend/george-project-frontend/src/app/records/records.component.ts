import { CommonModule } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Record } from '../record';
import { FileService } from '../file.service';
import {MatTableDataSource, MatTableModule} from '@angular/material/table'
import {MatSort, MatSortModule} from '@angular/material/sort'
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { EMPTY, Observable } from 'rxjs';
import { MongoFile } from '../mongo-file';
import { User } from '../user';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';


@Component({
  selector: 'app-records',
  standalone: true,
  imports: [MatGridListModule, MatCardModule, CommonModule, FormsModule, MatTableModule, MatSortModule, MatSelectModule, MatFormFieldModule],
  templateUrl: './records.component.html',
  styleUrl: './records.component.css'
})
export class RecordsComponent {
  records : Record[] = [];
  fields : any[] = [];
  fieldList : string[] = [];
  optionList : any[] = [];
  httpClient : HttpClient;
  fileName : string = '';
  showRecords : boolean = false;
  dataSource : any;
  filter : string = "";
  filters : string[] = ["Flat file", "Specification file", "User"]
  selected : any;

  constructor(httpClient : HttpClient, private fileService : FileService) {
    this.httpClient = httpClient;
  }

  @ViewChild(MatSort) sort : MatSort = new MatSort();


  selectFilter() {
    this.showRecords = false;
    console.log(this.filter);
    this.optionList = [];
    let response : Observable<Object> = EMPTY;
    switch(this.filter) {
      case "Flat file":
        console.log("flat");
        response = this.fileService.getAllFlatFiles();
        break;
      case "Specification file":
        console.log("spec");
        response = this.fileService.getAllSpecFiles();
        break;
      case "User" :
        console.log("User");
        response = this.fileService.getAllUsers();
        break;
      default:
        console.log("No filter selected");
        break;
    }
    if (this.filter != "User") {
      response.subscribe({
        next : (data : any) => {
          data.forEach((item : MongoFile) => {
            this.optionList.push(item.fileName);
          })
        }
      });
    } else {
      response.subscribe({
        next : (data : any) => {
          data.forEach((item : User) => {
            this.optionList.push(item.username);
          })
        }
      })
    }
    
  }

  view() {
    this.showRecords = true;
    this.records = [];
    this.fields = [];
    console.log(this.selected);
    let response : Observable<ArrayBuffer> = EMPTY;
    switch(this.filter) {
      case "Flat file":
        console.log("flat");
        response = this.fileService.getParsedRecordsByFlat(this.selected);
        break;
      case "Specification file":
        console.log("spec");
        response = this.fileService.getParsedRecordsBySpec(this.selected);
        break;
      case "User" :
        console.log("User");
        response = this.fileService.getParsedRecordsByUser(this.selected);
        break;
      default:
        console.log("No filter selected");
        break;
    }
    
    response.subscribe({
        next : (data : any) => {
          data.forEach((item : Record) => {
            this.records.push(item);
            console.log("item " + item);
          });

          this.fields = this.fileService.getFields(this.records);
          this.fieldList = this.fileService.getFieldList(this.fields);

        },
        error : (error : HttpErrorResponse) => {
          console.log("Error: ", error);
          this.fields = []
          this.fieldList = [];
          alert(error.message);
        },
        complete : () => {
          console.log("Response complete");
          this.dataSource = new MatTableDataSource(this.fields);
          this.dataSource.sort = this.sort;
        }
      });
  }

  getFieldList() : any {
    if (this.fields == null) {
      return;
    }
    this.fileService.getFieldList(this.fields);
  }

}
