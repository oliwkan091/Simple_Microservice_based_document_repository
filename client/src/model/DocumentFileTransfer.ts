export class DocumentFileTransfer {

  filename: string | undefined;
  fileSize: number | undefined;
  description: string| undefined;
  fileType: string | undefined;
  ownerId: number | undefined;
  file: Blob | undefined;

  constructor() {
    this.filename =  "";
    this.description =  "";
    this.fileSize = 0;
    this.fileType =  "";
    this.ownerId = 0;
    this.file = new Blob();
  }
}
