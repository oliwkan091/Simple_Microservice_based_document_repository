import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DocumentFile} from "../../../model/DocumentFile";
import {ManageFilesService} from "../../services/manage-files.service";

declare var $: any;

@Component({
  selector: 'app-file-edit',
  templateUrl: './file-edit.component.html',
  styleUrls: ['./file-edit.component.css']
})
export class FileEditComponent implements OnInit {

  fileToEdit: DocumentFile = new DocumentFile();
  originalFile: DocumentFile = new DocumentFile();
  errorMessage: string = "";

  @Output() save = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  showEditModalModel(file: DocumentFile)
  {
    this.originalFile = file;
    this.fileToEdit.construct(file)
    $("#editModal").modal("show");
  }

  saveFile()
  {
    this.originalFile.fileShowName = this.fileToEdit.fileShowName;
    this.originalFile.description = this.fileToEdit.description;
    this.save.emit();
    $("#editModal").modal("hide");
  }

  isDescriptionChanged(description: string| undefined): Boolean
  {
    if(this.fileToEdit.description === this.originalFile.description)
    {
      return false;
    }

    return true
  }

  isNameChanged(description: string| undefined): Boolean
  {
    if(this.fileToEdit.fileShowName === this.originalFile.fileShowName)
    {
      return false;
    }

    return true
  }
}
