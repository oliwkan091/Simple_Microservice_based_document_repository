import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {User} from "../../../model/User";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: String = "";

  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  ngOnInit(): void
  {
    if(this.authenticationService.currentUserValue?.id !== -1)
    {
      this.router.navigate(["/home"]);
      return;
    }
  }

  login()
  {
    this.authenticationService.login(this.user).subscribe(data => {
      this.router.navigate(["/home"]);
    }, error =>
    {
      console.log(error)
        this.errorMessage = "Username or password are incorrect";
    })
  }
}
