import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/User";
import {UserService} from "../../services/user.service";
import {AuthenticationService} from "../../services/authentication.service";
import {DocumentFile} from "../../../model/DocumentFile";
import {ManageFilesService} from "../../services/manage-files.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {

  errorMessage: string = "";
  userList: Array<User> = [];
  userFilesList: Array<DocumentFile> = [];
  selectedUser: string = "";
  constructor(private userService: UserService,private authenticationService: AuthenticationService, private manageFilesService :ManageFilesService) { }

  ngOnInit(): void {

    this.userList = [];
    this.userService.getAllUsers(this.authenticationService.currentUserValue).subscribe( data =>
    {
      this.userList = data;
    }, error =>
    {
      console.log("Error: " + error);
      this.errorMessage = error;
    })
  }

  BytesToKbOrMb(sizeInBytes: number): string
  {
    sizeInBytes = sizeInBytes / 1024;
    if(sizeInBytes > 999)
    {
      sizeInBytes = sizeInBytes / 1024;
      return sizeInBytes.toFixed(2).toString() + "MB"
    }else
    {
      return sizeInBytes.toFixed(2).toString() + "KB"
    }
  }

  checkIfFileTypeIsKnown(file: DocumentFile)
  {
    // @ts-ignore
    if(file.fileType.length != 0)
    {
      return file.fileType
    }else
    {
      return "Unknown"
    }
  }

  isUserSelected()
  {
    if(this.selectedUser === "Select")
    {
      return false;
    }else
    {
      return true;
    }
  }

  isRepositoryEmpty()
  {
    if((this.userFilesList?.length == undefined || this.userFilesList?.length === 0) && this.selectedUser != "Select")
    {
      return true;
    }else
    {
      return false;
    }
  }

  onSelectChange(event: Event)
  {
    // @ts-ignore
    this.selectedUser = event.target.value
    if(this.selectedUser === "Select")
    {
      this.userFilesList = [];
    }else {
      this.manageFilesService.getAllFilesByUsername(this.selectedUser).subscribe(
        data => {
          this.userFilesList = data;
        }, error => {
          this.errorMessage = error;
          console.log(error);
        }
      );
    }
  }

  fileCover(fileType: string = ""): string
  {
    if(fileType.search("image") != -1) {
      return "/assets/images/img.png";
    }
    else if(fileType.search("pdf") != -1)
    {
      return "/assets/images/Pdf.png";
    }
    else if(fileType.search("text") != -1)
    {
      return "/assets/images/text.png";
    }
    else
    {
      return "/assets/images/database.png";
    }
  }
}
