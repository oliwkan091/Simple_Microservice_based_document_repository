import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from "../../../model/User";
import {UserService} from "../../services/user.service";
import {RequestBaseService} from "../../services/request-base.service";
import {NgForm} from "@angular/forms";

declare var $: any;

@Component({
  selector: 'app-change-name',
  templateUrl: './change-name.component.html',
  styleUrls: ['./change-name.component.css']
})
export class ChangeNameComponent implements OnInit {

  errorMessage: string = "";
  userToEdit: User = new User();
  originalUser: User = new User();
  nameInformMessage: string = "";

  @Output() saveChangeName = new EventEmitter<any>();

  constructor(private userService: UserService, private requestBaseService :RequestBaseService) { }

  ngOnInit(): void {
  }

  showChangeUsernameModel(user: User)
  {
    this.originalUser = user;
    this.userToEdit.construct(user);
    $("#changeNameModal").modal("show");
  }

  saveUsername(form: NgForm)
  {
    this.userService.changeName(this.userToEdit).subscribe(data =>
    {
      this.saveChangeName.emit(data);
      this.requestBaseService.updateCurrentUser()
      form.resetForm();
      $("#changeNameModal").modal("hide");
    }, error =>
    {
      if(error.status = 409)
      {
        this.errorMessage = "Gievn user already exists";
      }else
      {
        this.errorMessage = error;
      }
      console.log(error)
    });
  }

  isNameChanged(): Boolean
  {
    if(this.originalUser.name === this.userToEdit.name)
    {
      this.nameInformMessage = "Name must be changed"
      return false;
    }else
    {
      return true;
    }
  }

  isNameEmpty(): Boolean
  {
    if(this.userToEdit.name.length != 0)
    {
      return false;
    }else
    {
      this.nameInformMessage = "Name cannot be empty"
      return true;
    }
  }
}
