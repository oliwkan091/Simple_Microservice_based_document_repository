import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthenticationService} from "./authentication.service";
import {User} from "../../model/User";

@Injectable({
  providedIn: 'root'
})
export abstract class RequestBaseService {

  protected currentUser: User = new User();

  protected constructor(protected authenticationService: AuthenticationService, protected http: HttpClient)
  {
    this.authenticationService.currentUser.subscribe(data =>
      {
        this.currentUser = data;
      }
    )
  }

  public updateCurrentUser()
  {
    this.authenticationService.currentUser.subscribe(data =>
    {
      this.currentUser = data;
    });
  }

  get getHeaders(): HttpHeaders
  {
    return  new HttpHeaders(
      {
        authorization: "Bearer " + this.currentUser?.token,
        "Content-Type": "application/json; charset=UTF-8"
      })
  }


  get getHeadersForFileUpload(): HttpHeaders
  {
    return  new HttpHeaders(
      {
        authorization: "Bearer " + this.currentUser?.token
      })
  }
}
