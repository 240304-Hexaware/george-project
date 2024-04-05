import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Record } from './record';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  httpClient : HttpClient
  currentUser : string = '';

  constructor(httpClient : HttpClient, private userService : UserService) { 
    this.httpClient = httpClient;
    this.userService.loggedIn$.subscribe(data => {
      this.currentUser = data.username;
    })
  }

  getAllFiles() : Observable<Object> {
    let url : string = "http://localhost:8080/api/file/all";
    let response = this.httpClient.get(url)
    return response;
  }

  getAllFlatFiles() : Observable<Object> {
    let url : string = "http://localhost:8080/api/file/flat";
    let response = this.httpClient.get(url)
    return response;
  }

  getAllSpecFiles() : Observable<Object> {
    let url : string = "http://localhost:8080/api/file/spec";
    let response = this.httpClient.get(url)
    return response;
  }
  
  getAllUsers() : Observable<Object> {
    let url : string = "http://localhost:8080/api/users"
    let response = this.httpClient.get(url);
    return response;
  }

  postParsedRecords(flat: string, spec: string) : Observable<ArrayBuffer> {
    // console.log("flat ", flat);
    // console.log("spec ", spec);
    let url : string = "http://localhost:8080/api/file/parse";
    const params = new HttpParams()
      .set("flat", flat)
      .set("spec", spec);
      
    let options : any = {
      params : params,
      headers: {
        username: this.currentUser,
      },
    }
    let response = this.httpClient.post(url, null, options);
    return response;
  }

  getParsedRecordsByFlat(flat : string) : Observable<ArrayBuffer> {
    let url : string = "http://localhost:8080/api/records/"
    const params = new HttpParams()
      .set("flat", flat)
    let options : any = {
      params : params
    }
    let response = this.httpClient.get(url, options)
    return response;
  }

  getParsedRecordsBySpec(spec : string) : Observable<ArrayBuffer> {
    let url : string = "http://localhost:8080/api/records/"
    const params = new HttpParams()
      .set("spec", spec)
    let options : any = {
      params : params
    }
    let response = this.httpClient.get(url, options)
    return response;
  }

  getParsedRecordsByUser(user : string) : Observable<ArrayBuffer> {
    let url : string = "http://localhost:8080/api/records/"
    const params = new HttpParams()
      .set("user", user)
    let options : any = {
      params : params
    }
    let response = this.httpClient.get(url, options)
    return response;
  }

  getParsedRecordsByFlatAndSpec(flat : string, spec : string) : Observable<ArrayBuffer> {
    let url : string = "http://localhost:8080/api/records/"
    const params = new HttpParams()
      .set("flat", flat)
      .set("spec", spec);
    
    let options : any = {
      params : params
    }
    let response = this.httpClient.get(url, options)
    return response;
  }

  getFields(records : Record[]) : Object[] {
    let fields : any[] = [];
    records.forEach(record => {
      fields.push(record.fields);
    })
    return fields;
  }

  getFieldList(fields : any[]) : string[] {
    return Object.keys(fields.at(0));
    
  }



}
