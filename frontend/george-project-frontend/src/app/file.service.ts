import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  httpClient : HttpClient

  constructor(httpClient : HttpClient) { 
    this.httpClient = httpClient;
  }

  


}
