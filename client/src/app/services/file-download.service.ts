import { Injectable } from '@angular/core';
import {RequestBaseService} from "./request-base.service";
import {AuthenticationService} from "./authentication.service";
import {HttpClient, HttpEvent, HttpEventType} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {DocumentFile} from "../../model/DocumentFile";
import {Observable} from "rxjs";

const API_URL = environment.BASE_URL + "/gateway/download/"

@Injectable({
  providedIn: 'root'
})
export class FileDownloadService extends RequestBaseService{

  constructor(authenticationService: AuthenticationService, http: HttpClient)
  {
    super(authenticationService, http)
  }

  downloadFile(file: DocumentFile): Observable<HttpEvent<Blob>> {
    return this.http.get(API_URL + file.fileId?.toString(),
    {
      reportProgress: true,
      observe: 'events',
      responseType: 'blob',
      headers: this.getHeaders
  });
  }


}
