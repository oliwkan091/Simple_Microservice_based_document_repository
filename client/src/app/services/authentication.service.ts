import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {BehaviorSubject, map, Observable} from "rxjs";
import {User} from "../../model/User";

const API_URL = environment.BASE_URL + "/api/authentication"
const currentUserString = "currentUser";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient)
  {
    let storageUser;
    const storageUserAsStr = localStorage.getItem(currentUserString)
    if (storageUserAsStr)
    {
      storageUser = JSON.parse(storageUserAsStr);
    }

    this.currentUserSubject = new BehaviorSubject<User>(storageUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  changeName(newUser: User)
  {
    localStorage.removeItem(currentUserString)
    localStorage.setItem(currentUserString, JSON.stringify(newUser));
    this.currentUserSubject.next(newUser);
  }


  public get currentUserValue(): User
  {
    return this.currentUserSubject.value;
  }

  login(user: User): Observable<any>
  {
    return this.http.post<any>(API_URL + "/singIn", user).pipe(
      map(response =>
      {
        if (response)
        {
          localStorage.setItem(currentUserString, JSON.stringify(response));
          this.currentUserSubject.next(response);
        }
        return response
      })
    )
  }

  logout()
  {
    localStorage.removeItem(currentUserString)
    this.currentUserSubject.next(new User());
  }

  register(user: User): Observable<any>
  {
    return this.http.post<any>(API_URL + "/singUp", user);
  }
}
