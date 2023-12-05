import { Injectable } from '@angular/core';
import {RequestBaseService} from "./request-base.service";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DocumentFile} from "../../model/DocumentFile";

const API_URL = environment.BASE_URL + "/gateway/manage/"

@Injectable({
  providedIn: 'root'
})
export class ManageFilesService extends RequestBaseService{

  constructor(authenticationService: AuthenticationService, http: HttpClient)
  {
    super(authenticationService, http);
  }

  getAllUserFiles(): Observable<any>
  {
    return this.http.post(API_URL, this.currentUser, {headers: this.getHeaders})
  }

  deleteFileById(file: DocumentFile): Observable<any>
  {
    return this.http.delete(API_URL + file.fileId?.toString(), {headers: this.getHeaders})
  }

  searchFile(text: string): Observable<any>
  {
    return this.http.get(API_URL + text, {headers: this.getHeaders});
  }

  saveEditedFile(file: DocumentFile): Observable<any>
  {
    return this.http.post(API_URL + "save", file, {headers: this.getHeaders});
  }

  getAllFilesByUsername(username: string): Observable<any>
  {
    return this.http.post(API_URL + "getAllFilesByUsername", username, {headers: this.getHeaders})
  }
}
