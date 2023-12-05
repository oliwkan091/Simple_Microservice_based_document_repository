import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../../model/User";
import {ChangeNameComponent} from "../../components/change-name/change-name.component";
import {ManageFilesService} from "../../services/manage-files.service";
import {ChangePasswordComponent} from "../../components/change-password/change-password.component";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User = new User()

  @ViewChild(ChangeNameComponent)
  changeNameComponent: ChangeNameComponent | undefined;

  @ViewChild(ChangePasswordComponent)
  changePasswordComponent: ChangePasswordComponent | undefined;

  constructor(private authenticationService: AuthenticationService, private manageFilesService: ManageFilesService, private userService: UserService) { }

  ngOnInit(): void {

    this.userService.isSignIn().subscribe(data => {});

    this.authenticationService.currentUser.subscribe(data =>
      {
        this.user = data;
      }, error =>
      {
        console.log(error);
      }
    );
  }

  editUsername()
  {
    this.changeNameComponent?.showChangeUsernameModel(this.user);
  }

  editPassword()
  {
    this.changePasswordComponent?.showChangePasswordModel(this.user);
  }

  saveEditedName(user: User)
  {
    this.user = user;
    this.authenticationService.changeName(user);
  }
}
