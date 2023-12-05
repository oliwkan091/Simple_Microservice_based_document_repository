import { Injectable } from '@angular/core';
import {RequestBaseService} from "./request-base.service";
import {AuthenticationService} from "./authentication.service";
import {HttpClient} from "@angular/common/http";
import {User} from "../../model/User";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

const API_URL = environment.BASE_URL + "/gateway/user/"

@Injectable({
  providedIn: 'root'
})
export class UserService  extends RequestBaseService{

  constructor(authenticationService: AuthenticationService, http: HttpClient)
  {
    super(authenticationService, http);
  }

  changeName(user: User): Observable<any>
  {
    return this.http.post(API_URL + "changeName", user, {headers: this.getHeaders});
  }

  changePassword(againPassword: String, currenPassword: String): Observable<any>
  {
    let passwords: Array<String> = [againPassword, currenPassword]
    return this.http.post(API_URL + "changePassword", passwords, {headers: this.getHeaders});
  }

  isSignIn(): Observable<any>
  {
    return this.http.post(API_URL + "isSignIn", "a",{headers: this.getHeaders});
  }

  getAllUsers(user: User): Observable<any>
  {
    return this.http.get(API_URL + "getAllUsers",{headers: this.getHeaders});
  }
}
