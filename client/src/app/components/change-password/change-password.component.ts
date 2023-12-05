import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {User} from "../../../model/User";
import {ManageFilesService} from "../../services/manage-files.service";
import {ChangeNameComponent} from "../change-name/change-name.component";
import {UserService} from "../../services/user.service";
import {FormGroup, NgForm} from "@angular/forms";

declare var $: any;

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  errorMessage: string = "";
  passwordToEdit: User = new User();

  currentPassword: string = "";
  currentPasswordAlert: string = "";
  firstPassword: string = "";
  firstPasswordAlert: string = "";
  againPassword: string = "";
  againPasswordAlert: string = "";
  passwordIsRequired: string = "";

  @Output() saveChangePassword = new EventEmitter<any>();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  showChangePasswordModel(user: User)
  {
    $("#changePasswordModal").modal("show");
  }

  savePassword(form: NgForm)
  {
    this.userService.changePassword(this.againPassword, this.currentPassword).subscribe(data =>
    {
      this.saveChangePassword.emit(data);
      form.resetForm();
      $("#changePasswordModal").modal("hide");
    }, error =>
    {
      if(error.status == 409)
      {
        this.errorMessage = "Current password is wrong"
      }else
      {
        this.errorMessage = error;
      }
      console.log("error")
      console.log(error)
    });
  }

  isPasswordEmpty(password: string): Boolean
  {this.passwordIsRequired = "Password is required"
    if(password.length == 0)
    {
      this.passwordIsRequired = "Password is required"
      return true;
    }else
    {
      return false;
    }
  }

  arePasswordsIdentical(password1: string, password2: string): Boolean
  {
    if(password1 === password2)
    {
     return true;
    }else
    {
      return false;
    }
  }
}
