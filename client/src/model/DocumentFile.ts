export class DocumentFile {

  id: number| undefined;
  fileId: string| undefined;
  filename: string| undefined;
  fileShowName: string| undefined;
  description: string| undefined;
  pathToFile:string| undefined;
  sendDate: Date| undefined;
  fileSize: number| undefined;
  fileType: string| undefined;
  ownerId: number| undefined;

  constructor() {
    this.id = 0;
    this.fileId = "";
    this.filename =  "";
    this.fileShowName =  "";
    this.description =  "";
    this.pathToFile =  "";
    this.sendDate = new Date();
    this.fileSize = 0;
    this.fileType =  "";
    this.ownerId = 0;
  }

   construct(updatedFile: DocumentFile | null)
  {
    this.fileId = updatedFile?.fileId;
    // @ts-ignore
    this.id = updatedFile.id;
    // @ts-ignore
    this.filename = updatedFile.filename;
    // @ts-ignore
    this.fileShowName = updatedFile.fileShowName;
    // @ts-ignore
    this.description = updatedFile.description;
    // @ts-ignore
    this.pathToFile = updatedFile.pathToFile;
    // @ts-ignore
    this.sendDate = updatedFile.sendDate;
    // @ts-ignore
    this.fileSize = updatedFile.fileSize;
    // @ts-ignore
    this.fileType = updatedFile.fileType;
    // @ts-ignore
    this.ownerId = updatedFile.ownerId;
  }
}
