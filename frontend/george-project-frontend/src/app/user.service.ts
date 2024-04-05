import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable, Input } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  httpClient : HttpClient;
  loggedInSubject: BehaviorSubject<User> = new BehaviorSubject<User>({loggedIn: false, username: '', password: ''});
  public loggedIn$: Observable<User> = this.loggedInSubject.asObservable();

  constructor(httpClient : HttpClient) {this.httpClient = httpClient;}

  register(username : string, password : string) {
    let url : string = "http://localhost:8080/api/register";
    const params = new HttpParams()
      .set("username", username)
      .set("password", password);
  
    let options : any = {
      params : params
    }
    this.httpClient.post(url, null, options)
      .subscribe({
        next : (data : any) => {
          this.loggedInSubject.next({loggedIn: true, username: data.username, password: data.password});
        },
        error : (error : HttpErrorResponse) => {
          console.log(error);
          alert("Username already exists");
        },
        complete : () => {
          console.log("Response complete");
        }
      });
  }

  login(username : string, password : string) {
    let url : string = "http://localhost:8080/api/login";
    const params = new HttpParams()
      .set("username", username)
      .set("password", password);
  
    let options : any = {
      params : params
    }
    this.httpClient.post(url, null, options)
      .subscribe({
        next : (data : any) => {
          this.loggedInSubject.next({loggedIn: true, username: data.username, password: data.password});
        },
        error : (error : HttpErrorResponse) => {
          console.log(error);
          alert("Invalid username or password");
        },
        complete : () => {
          console.log("Response complete");
        }
      });
  }

  isLoggedIn() : boolean {
    let check : boolean = false;
    this.loggedIn$.subscribe(data=>{
      check = data.loggedIn;
    });
    return check;
  }

  // getCurrentUser() : string {
  //   return this.currentUser;
  // }

  logout() {
    this.loggedInSubject.next({loggedIn: false, username: '', password:''});
  }
}
