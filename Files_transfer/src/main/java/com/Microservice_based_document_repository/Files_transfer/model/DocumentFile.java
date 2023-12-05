package com.Microservice_based_document_repository.Files_transfer.model;
import com.Microservice_based_document_repository.Files_transfer.enviroment.Varibles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Model of file saved in database
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "files")
public class DocumentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="file_id", nullable = false)
    private UUID fileId;

    @Column(name ="filename", nullable = false)
    private String filename;

    @Column(name ="file_show_name", nullable = false)
    private String fileShowName;

    @Column(name ="description", nullable = false)
    private String description;

    @Column(name ="path_to_file", nullable = false)
    private String pathToFile;

    @Column(name ="send_date", nullable = false)
    private LocalDateTime sendDate;

    @Column(name ="file_size", nullable = false)
    private Long fileSize;

    @Column(name ="file_type", nullable = false)
    private String fileType;

    @Column(name ="owner_id", nullable = false)
    private Long ownerId;

    @Column(name ="search_name", nullable = false)
    private String searchName;

    /**
     * Model Construction from MultipartFile and userId
     * @param multipartFile binary file with metadata
     * @param ownerId ID of owner
     */
    public DocumentFile(MultipartFile multipartFile, Long ownerId) {

        this.fileId = UUID.randomUUID();
        this.filename = multipartFile.getOriginalFilename();
        this.pathToFile = Varibles.pathToRepo + "\\" + fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.searchName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
    }

    /**
     * Model Construction from MultipartFile, userId and description if was provided befor upload
     * @param multipartFile binary file with metadata
     * @param ownerId ID of owner
     * @param description description of file inserted before upload
     */
    public DocumentFile(MultipartFile multipartFile, Long ownerId, String description) {

        this.fileId = UUID.randomUUID();
        this.filename = multipartFile.getOriginalFilename();
        this.pathToFile = Varibles.pathToRepo + "\\" + fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.searchName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.description = description;
    }

    /**
     * Model Construction from DocumentFileTransfer model
     * @param documentFileTransfer model which holds binary file
     */
    public DocumentFile(DocumentFileTransfer documentFileTransfer) {

        this.fileId = UUID.randomUUID();
        this.filename = documentFileTransfer.getFilename();
        this.pathToFile = /*Varibles.pathToRepo + "\\" + */fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = documentFileTransfer.getFileSize();
        this.fileType = documentFileTransfer.getFileType();
        this.ownerId = documentFileTransfer.getOwnerId();
        this.searchName =documentFileTransfer.getFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = documentFileTransfer.getFilename().replaceAll("[.][a-zA-z]+","");
        if(documentFileTransfer.getDescription() == null)
        {
            this.description = "";
        }else
        {
            this.description = documentFileTransfer.getDescription();
        }
    }

    /**
     * Generates search data of name of file and tags of file
     */
    public void generateSearchName()
    {
        this.searchName = filename.replaceAll("[.][a-zA-z]+","") + " " + description;
        this.searchName = this.searchName.replaceAll("[\\W]"," ");
        System.out.println("this.searchName");
        System.out.println(this.searchName);
    }

    /**
     * Generates name of file that will be shown. Cuts file extension
     */
    public void generateFileShowName()
    {
        this.fileShowName = filename.replaceAll("[.][a-zA-z]+","");
    }

    /**
     * Changes file name. Cuts file extension
     */
    public void changeFilename()
    {
        this.filename = this.fileShowName + this.filename.replaceAll(Varibles.regexGetFileExtensionWithoutDot,".");
    }
}
