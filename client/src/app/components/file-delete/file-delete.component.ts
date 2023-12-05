import {Component, EventEmitter, OnInit, Output} from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-file-delete',
  templateUrl: './file-delete.component.html',
  styleUrls: ['./file-delete.component.css']
})
export class FileDeleteComponent implements OnInit {

  @Output() confirmed = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  deleteFile()
  {
    this.confirmed.emit();
    $("#deleteModal").modal("hide");
  }

  showDeleteModel()
  {
    $("#deleteModal").modal("show");
  }
}
