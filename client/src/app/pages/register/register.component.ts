import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {User} from "../../../model/User";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  passwordAgain: string = "";
  errorMessage: string = "";

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit(): void
  {}

  register()
  {
    if (this.user.name === this.passwordAgain) {
      this.authenticationService.register(this.user).subscribe(data => {
        this.authenticationService.login(this.user).subscribe(data => {
          this.router.navigate(["/home"]);
        }, error =>
        {
          console.log(error);
          this.errorMessage = error;
        });
      }, error => {
        if (error?.status === 409) {
          this.errorMessage = "Username already exists";
        } else if (error?.status === 400) {
          console.log("Error 400");
        } else {
          console.log("Error");
          this.errorMessage = "Unexpected error";
        }
      })
    }else
    {
      this.errorMessage = "Password must by identical"
    }
  }

  isPasswordIdentical(): Boolean
    {
      if(this.user.password === this.passwordAgain)
      {
        return true;
      }else
      {
        return false;
      }
    }
}
