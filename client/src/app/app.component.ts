import { Component } from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";
import {Router} from "@angular/router";
import {User} from "../model/User";
import {Role} from "../model/Role";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  currentUser: User = new User;

  constructor(private authenticationService: AuthenticationService, private router:Router)
  {
    authenticationService.currentUser.subscribe(data =>
      {
        this.currentUser = data;
      }
    );
  }

  isLogIn(): Boolean
  {

    this.authenticationService.currentUser.subscribe(data =>
      {
        this.currentUser = data;
      }
    );

    if(this.currentUser?.id != undefined && this.currentUser?.id != -1)
    {
      return true
    }else
      return false;
  }

  logOut()
  {
    this.authenticationService.logout();
    this.router.navigate(["/login"]);
  }

  isAdmin()
  {
    return this.currentUser?.role === Role.ADMIN;
  }
}
