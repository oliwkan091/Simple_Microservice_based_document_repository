import { Injectable } from '@angular/core';
import {RequestBaseService} from "./request-base.service";
import {AuthenticationService} from "./authentication.service";
import {HttpClient, HttpEvent, HttpHeaders, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {DocumentFile} from "../../model/DocumentFile";

const API_URL = environment.BASE_URL + "/gateway/upload"

@Injectable({
  providedIn: 'root'
})
export class FileUploadService extends RequestBaseService{

  constructor(authenticationService: AuthenticationService, http: HttpClient)
  {
    super(authenticationService, http)
  }

  upload(formData: FormData): Observable<HttpResponse<DocumentFile>>
  {
    return this.http.post<DocumentFile>(API_URL, formData,
    {
      headers: this.getHeadersForFileUpload,
      reportProgress: true,
      observe: 'response'
    })
  }
}
